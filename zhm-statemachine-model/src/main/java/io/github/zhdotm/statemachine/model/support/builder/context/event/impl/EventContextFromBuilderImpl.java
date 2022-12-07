package io.github.zhdotm.statemachine.model.support.builder.context.event.impl;

import io.github.zhdotm.statemachine.model.domain.IEvent;
import io.github.zhdotm.statemachine.model.domain.IEventContext;
import io.github.zhdotm.statemachine.model.domain.impl.EventContextImpl;
import io.github.zhdotm.statemachine.model.support.builder.context.event.IEventContextFromBuilder;
import lombok.AllArgsConstructor;

/**
 * @author zhihao.mao
 */

@AllArgsConstructor
public class EventContextFromBuilderImpl<S, E> implements IEventContextFromBuilder<S, E> {

    private final EventContextImpl<S, E> eventContext;

    public static <S, E> EventContextFromBuilderImpl<S, E> getInstance(EventContextImpl<S, E> eventContext) {

        return new EventContextFromBuilderImpl<>(eventContext);
    }

    @Override
    public IEventContext<S, E> on(IEvent<E> event) {

        return eventContext.on(event);
    }

}
