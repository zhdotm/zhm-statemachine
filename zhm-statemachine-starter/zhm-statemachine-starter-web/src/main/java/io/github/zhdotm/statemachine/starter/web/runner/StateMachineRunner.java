package io.github.zhdotm.statemachine.starter.web.runner;

import io.github.zhdotm.statemachine.model.domain.IStateMachine;
import io.github.zhdotm.statemachine.model.domain.ITransition;
import io.github.zhdotm.statemachine.model.support.StateMachineFactory;
import io.github.zhdotm.statemachine.model.support.builder.machine.IStateMachineBuilder;
import io.github.zhdotm.statemachine.starter.web.adapter.ITransitionAdapter;
import io.github.zhdotm.statemachine.starter.web.annotation.StateMachineComponent;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhihao.mao
 */

public class StateMachineRunner implements ApplicationRunner, BeanFactoryPostProcessor {

    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Map<String, List<ITransition<String, String, String, String>>> stateMachineIdTransitionsMap = getStateMachineIdTransitionsMap();

        stateMachineIdTransitionsMap.forEach((stateMachineId, transitions) -> {
            IStateMachineBuilder<String, String, String, String, String> stateMachineBuilder = StateMachineFactory.create();
            stateMachineBuilder.transitions(transitions);
            IStateMachine<String, String, String, String, String> stateMachine = stateMachineBuilder.build(stateMachineId);

            registerStateMachine(stateMachineId, stateMachine);
        });
    }

    private void registerStateMachine(String stateMachineId, IStateMachine<String, String, String, String, String> stateMachine) {

        beanFactory.registerSingleton(stateMachineId, stateMachine);
    }

    private Map<String, List<ITransition<String, String, String, String>>> getStateMachineIdTransitionsMap() {
        Map<String, List<ITransition<String, String, String, String>>> stateMachineIdTransitionsMap = new HashMap<>();

        Map<String, Object> beansWithAnnotation = beanFactory.getBeansWithAnnotation(StateMachineComponent.class);
        for (Object bean : beansWithAnnotation.values()) {
            ITransitionAdapter transitionAdapter = (ITransitionAdapter) bean;
            String stateMachineId = transitionAdapter.getStateMachineId();
            List<ITransition<String, String, String, String>> transitions = stateMachineIdTransitionsMap.getOrDefault(stateMachineId, new ArrayList<>());
            ITransition<String, String, String, String> transition = transitionAdapter.getTransition();
            transitions.add(transition);
            stateMachineIdTransitionsMap.put(stateMachineId, transitions);
        }

        return stateMachineIdTransitionsMap;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

        beanFactory = configurableListableBeanFactory;
    }

}
