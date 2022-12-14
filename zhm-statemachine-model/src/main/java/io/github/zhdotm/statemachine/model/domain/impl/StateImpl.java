package io.github.zhdotm.statemachine.model.domain.impl;

import io.github.zhdotm.statemachine.model.domain.IState;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author zhihao.mao
 */

public class StateImpl<S, E> implements IState<S, E> {

    @Getter
    @Setter
    private S stateId;

    @Getter
    @Setter
    private Collection<E> eventIds = new HashSet<>();

    public static <S, E> StateImpl<S, E> getInstance() {

        return new StateImpl<>();
    }

    @Override
    public StateImpl<S, E> stateId(@NonNull S stateId) {
        this.stateId = stateId;

        return this;
    }

    @Override
    public StateImpl<S, E> addEventId(@NonNull E eventId) {
        eventIds.add(eventId);

        return this;
    }

}
