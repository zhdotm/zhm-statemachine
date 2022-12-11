package io.github.zhdotm.statemachine.starter.web.runner;

import io.github.zhdotm.statemachine.model.domain.IStateMachine;
import io.github.zhdotm.statemachine.model.domain.ITransition;
import io.github.zhdotm.statemachine.model.support.StateMachineFactory;
import io.github.zhdotm.statemachine.model.support.builder.machine.IStateMachineBuilder;
import io.github.zhdotm.statemachine.starter.web.adapter.ITransitionAdapter;
import io.github.zhdotm.statemachine.starter.web.annotation.StateMachineComponent;
import io.github.zhdotm.statemachine.starter.web.configuration.properties.StateMachineConfigurationProperties;
import io.github.zhdotm.statemachine.starter.web.support.StateMachineSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

@Slf4j
public class StateMachineRunner implements ApplicationRunner {

    @Autowired
    private StateMachineConfigurationProperties stateMachineConfigurationProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Map<String, List<ITransition<String, String, String, String>>> stateMachineIdTransitionsMap = getStateMachineIdTransitionsMap();

        stateMachineIdTransitionsMap.forEach((stateMachineId, transitions) -> {
            IStateMachineBuilder<String, String, String, String, String> stateMachineBuilder = StateMachineFactory.create();
            stateMachineBuilder.transitions(transitions);
            IStateMachine<String, String, String, String, String> stateMachine = stateMachineBuilder.build(stateMachineId);

            if (stateMachineConfigurationProperties.getPrint()) {
                log.info("打印状态机[{}]内部结构: 开始", stateMachineId);
                stateMachine.print();
                log.info("打印状态机[{}]内部结构: 结束", stateMachineId);
            }

            StateMachineSupport.registerStateMachine(stateMachine);
        });
    }

    private Map<String, List<ITransition<String, String, String, String>>> getStateMachineIdTransitionsMap() {
        Map<String, List<ITransition<String, String, String, String>>> stateMachineIdTransitionsMap = new HashMap<>();

        ConfigurableListableBeanFactory beanFactory = StateMachineSupport.getBeanFactory();
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

}
