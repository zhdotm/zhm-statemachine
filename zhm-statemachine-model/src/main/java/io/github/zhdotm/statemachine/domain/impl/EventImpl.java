package io.github.zhdotm.statemachine.domain.impl;

import io.github.zhdotm.statemachine.domain.IEvent;
import lombok.Getter;

/**
 * @author zhihao.mao
 */

public class EventImpl<E> implements IEvent<E> {

    @Getter
    private E eventId;

    @Getter
    private Object[] payload;

    public static <E> EventImpl<E> getInstance() {

        return new EventImpl<>();
    }

    public EventImpl<E> eventId(E eventId) {
        this.eventId = eventId;

        return this;
    }

    public EventImpl<E> payload(Object... payload) {
        this.payload = payload;

        return this;
    }

}
