package io.github.zhdotm.statemachine.model.support.builder.event.impl;

import io.github.zhdotm.statemachine.model.domain.IEvent;
import io.github.zhdotm.statemachine.model.domain.impl.EventImpl;
import io.github.zhdotm.statemachine.model.support.builder.event.IEventPayloadBuilder;
import lombok.AllArgsConstructor;

/**
 * @author zhihao.mao
 */

@AllArgsConstructor
public class EventPayloadBuilderImpl<E> implements IEventPayloadBuilder<E> {

    private final EventImpl<E> event;

    public static <E> EventPayloadBuilderImpl<E> getInstance(EventImpl<E> event) {

        return new EventPayloadBuilderImpl<>(event);
    }

    @Override
    public IEvent<E> build(E eventId) {

        return event.eventId(eventId);
    }

}
