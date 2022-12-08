package io.github.zhdotm.statemachine.starter.web.support;

import io.github.zhdotm.statemachine.model.domain.IEventContext;
import io.github.zhdotm.statemachine.model.domain.IStateContext;
import io.github.zhdotm.statemachine.model.domain.IStateMachine;
import io.github.zhdotm.statemachine.model.domain.impl.EventContextImpl;
import io.github.zhdotm.statemachine.model.domain.impl.EventImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * @author zhihao.mao
 */

public class StateMachineSupport implements BeanFactoryPostProcessor {

    private static ConfigurableListableBeanFactory beanFactory;

    public static <M, S, E, C, A> IStateMachine<M, S, E, C, A> getStateMachine(M stateMachineId) {

        return beanFactory.getBean(String.valueOf(stateMachineId), IStateMachine.class);
    }

    public static <M, S, E, C, A> void registerStateMachine(IStateMachine<M, S, E, C, A> stateMachine) {

        beanFactory.registerSingleton(stateMachine.getStateMachineId().toString(), stateMachine);
    }

    public static <M, S, E, C, A> IStateContext<S, E> fireEvent(M stateMachineId, S stateId, E eventId, Object... payload) {
        EventContextImpl<S, E> eventContext = EventContextImpl.getInstance();
        EventImpl<E> event = EventImpl.getInstance();
        event.eventId(eventId)
                .payload(payload);
        eventContext.from(stateId)
                .on(event);

        return fireEvent(stateMachineId, eventContext);
    }

    public static <M, S, E, C, A> IStateContext<S, E> fireEvent(M stateMachineId, IEventContext<S, E> eventContext) {
        IStateMachine<M, S, E, C, A> stateMachine = getStateMachine(stateMachineId);

        return stateMachine.fireEvent(eventContext);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

        StateMachineSupport.beanFactory = configurableListableBeanFactory;
    }

}
