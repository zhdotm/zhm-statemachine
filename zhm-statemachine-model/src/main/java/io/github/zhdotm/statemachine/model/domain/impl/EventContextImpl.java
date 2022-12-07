package io.github.zhdotm.statemachine.model.domain.impl;

import io.github.zhdotm.statemachine.model.domain.IEvent;
import io.github.zhdotm.statemachine.model.domain.IEventContext;
import lombok.Getter;
import lombok.NonNull;

/**
 * @author zhihao.mao
 */

public class EventContextImpl<S, E> implements IEventContext<S, E> {

    @Getter
    private S stateId;

    @Getter
    private IEvent<E> event;

    public static <S, E> EventContextImpl<S, E> getInstance() {

        return new EventContextImpl<>();
    }

    public EventContextImpl<S, E> from(@NonNull S stateId) {
        this.stateId = stateId;

        return this;
    }

    public EventContextImpl<S, E> on(@NonNull IEvent<E> event) {
        this.event = event;

        return this;
    }

}
