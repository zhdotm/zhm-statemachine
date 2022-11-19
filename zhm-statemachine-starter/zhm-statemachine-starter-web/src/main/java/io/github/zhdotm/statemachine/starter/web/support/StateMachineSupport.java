package io.github.zhdotm.statemachine.starter.web.support;

import io.github.zhdotm.statemachine.model.domain.IEventContext;
import io.github.zhdotm.statemachine.model.domain.IStateContext;
import io.github.zhdotm.statemachine.model.domain.IStateMachine;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author zhihao.mao
 */

public class StateMachineSupport implements ApplicationContextAware {

    private static ConfigurableListableBeanFactory beanFactory;

    public static <M, S, E, C, A> IStateMachine<M, S, E, C, A> getStateMachine(M stateMachineId) {

        return beanFactory.getBean(stateMachineId.toString(), IStateMachine.class);
    }

    public static <M, S, E, C, A> void registerStateMachine(IStateMachine<M, S, E, C, A> stateMachine) {

        beanFactory.registerSingleton(stateMachine.getStateMachineId().toString(), stateMachine);
    }

    public static <M, S, E, C, A> IStateContext<S, E> fireEvent(M stateMachineId, IEventContext<S, E> eventContext) {
        IStateMachine<M, S, E, C, A> stateMachine = getStateMachine(stateMachineId);

        return stateMachine.fireEvent(eventContext);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        StateMachineSupport.beanFactory = (ConfigurableListableBeanFactory) applicationContext;
    }

}
