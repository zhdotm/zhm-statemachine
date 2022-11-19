package io.github.zhdotm.statemachine.starter.web.runner;

import io.github.zhdotm.statemachine.model.domain.IStateMachine;
import io.github.zhdotm.statemachine.model.domain.ITransition;
import io.github.zhdotm.statemachine.model.support.StateMachineFactory;
import io.github.zhdotm.statemachine.model.support.builder.StateMachineBuilder;
import io.github.zhdotm.statemachine.starter.web.adapter.ITransitionAdapter;
import io.github.zhdotm.statemachine.starter.web.annotation.StateMachineComponent;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhihao.mao
 */

public class StateMachineRunner implements ApplicationRunner, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Map<String, List<ITransition<String, String, String, String>>> stateMachineIdTransitionsMap = getStateMachineIdTransitionsMap();

        stateMachineIdTransitionsMap.forEach((stateMachineId, transitions) -> {
            StateMachineBuilder<String, String, String, String, String> stateMachineBuilder = StateMachineFactory.create();
            stateMachineBuilder.transitions(transitions);
            IStateMachine<String, String, String, String, String> stateMachine = stateMachineBuilder.build(stateMachineId);

            registerStateMachine(stateMachineId, stateMachine);
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        this.applicationContext = applicationContext;
    }

    private void registerStateMachine(String stateMachineId, IStateMachine<String, String, String, String, String> stateMachine) {

        ConfigurableListableBeanFactory beanFactory = (ConfigurableListableBeanFactory) applicationContext;
        beanFactory.registerSingleton(stateMachineId, stateMachine);
    }

    private Map<String, List<ITransition<String, String, String, String>>> getStateMachineIdTransitionsMap() {
        Map<String, List<ITransition<String, String, String, String>>> stateMachineIdTransitionsMap = new HashMap<>();

        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(StateMachineComponent.class);
        beansWithAnnotation
                .values()
                .stream()
                .map(bean -> (ITransitionAdapter) bean)
                .collect(Collectors.groupingBy(ITransitionAdapter::getStateMachineId))
                .forEach((stateMachineId, transitionAdapters) -> {
                    List<ITransition<String, String, String, String>> transitions = transitionAdapters
                            .stream()
                            .map(ITransitionAdapter::getTransition)
                            .collect(Collectors.toList());
                    stateMachineIdTransitionsMap.put(stateMachineId, transitions);
                });

        return stateMachineIdTransitionsMap;
    }

}
