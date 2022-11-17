package io.github.zhdotm.statemachine.domain.impl;

import io.github.zhdotm.statemachine.domain.IEvent;
import io.github.zhdotm.statemachine.domain.IEventContext;
import lombok.Getter;

public class EventContextImpl<S, E> implements IEventContext<S, E> {

    @Getter
    private S stateId;

    @Getter
    private IEvent<E> event;

    public static <S, E> EventContextImpl<S, E> getInstance() {

        return new EventContextImpl<>();
    }

    public EventContextImpl<S, E> stateId(S stateId) {
        this.stateId = stateId;

        return this;
    }

    public EventContextImpl<S, E> event(IEvent<E> event) {
        this.event = event;

        return this;
    }
    
}
