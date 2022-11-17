package io.github.zhdotm.statemachine.domain.impl;

import io.github.zhdotm.statemachine.domain.IState;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class StateImpl<S, E> implements IState<S, E> {

    @Getter
    private S stateId;

    @Getter
    private final List<E> eventIds = new ArrayList<>();

    public static <S, E> StateImpl<S, E> getInstance() {

        return new StateImpl<>();
    }

    public StateImpl<S, E> stateId(S stateId) {
        this.stateId = stateId;

        return this;
    }

    public StateImpl<S, E> addEventId(E eventId) {
        eventIds.add(eventId);

        return this;
    }
    
}
