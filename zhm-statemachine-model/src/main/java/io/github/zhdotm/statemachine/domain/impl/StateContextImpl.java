package io.github.zhdotm.statemachine.domain.impl;

import io.github.zhdotm.statemachine.domain.IEventContext;
import io.github.zhdotm.statemachine.domain.IStateContext;
import lombok.Getter;

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

    public StateContextImpl<S, E> to(S stateId) {
        this.stateId = stateId;

        return this;
    }

    public StateContextImpl<S, E> result(Object payload) {
        this.payload = payload;

        return this;
    }

    public StateContextImpl<S, E> eventContext(IEventContext<S, E> eventContext) {
        this.eventContext = eventContext;

        return this;
    }

}
