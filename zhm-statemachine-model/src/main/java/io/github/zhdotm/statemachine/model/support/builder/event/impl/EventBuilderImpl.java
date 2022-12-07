package io.github.zhdotm.statemachine.model.support.builder.event.impl;

import io.github.zhdotm.statemachine.model.domain.impl.EventImpl;
import io.github.zhdotm.statemachine.model.support.builder.event.IEventBuilder;
import io.github.zhdotm.statemachine.model.support.builder.event.IEventPayloadBuilder;

/**
 * @author zhihao.mao
 */

public class EventBuilderImpl<E> implements IEventBuilder<E> {

    public static <E> EventBuilderImpl<E> getInstance() {

        return new EventBuilderImpl<>();
    }

    @Override
    public IEventPayloadBuilder<E> payload(Object... objs) {
        EventImpl<E> event = EventImpl.getInstance();
        event.payload(objs);

        return EventPayloadBuilderImpl.getInstance(event);
    }

}
