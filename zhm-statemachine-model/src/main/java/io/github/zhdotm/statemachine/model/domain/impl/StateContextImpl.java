package io.github.zhdotm.statemachine.model.domain.impl;

import io.github.zhdotm.statemachine.model.domain.IEventContext;
import io.github.zhdotm.statemachine.model.domain.IStateContext;
import lombok.Getter;
import lombok.NonNull;

public class StateContextImpl<S, E> implements IStateContext<S, E> {

    @Getter
    private S stateId;

    @Getter
    private Object payload;

    @Getter
    private IEventContext<S, E> eventContext;

    public static <S, E> StateContextImpl<S, E> getInstance() {

        return new StateContextImpl<>();
    }

    public StateContextImpl<S, E> to(@NonNull S stateId) {
        this.stateId = stateId;

        return this;
    }

    public StateContextImpl<S, E> result(Object payload) {
        this.payload = payload;

        return this;
    }

    public StateContextImpl<S, E> eventContext(@NonNull IEventContext<S, E> eventContext) {
        this.eventContext = eventContext;

        return this;
    }

}
