package io.github.zhdotm.statemachine.domain.impl;

import io.github.zhdotm.statemachine.domain.IAction;
import io.github.zhdotm.statemachine.domain.ICondition;
import io.github.zhdotm.statemachine.domain.ITransition;
import lombok.Getter;

import java.util.*;

public class TransitionImpl<S, E, A> implements ITransition<S, E, A> {

    @Getter
    private Collection<S> fromStateIds = new HashSet<>();

    @Getter
    private S toStateId;

    @Getter
    private ICondition<S, E> condition;

    @Getter
    private IAction<A> action;

    public static <S, E, A> TransitionImpl<S, E, A> getInstance() {

        return new TransitionImpl<>();
    }

    public TransitionImpl<S, E, A> from(S... stateIds) {
        fromStateIds.addAll(Arrays.asList(stateIds));

        return this;
    }

    public TransitionImpl<S, E, A> to(S stateId) {
        toStateId = stateId;

        return this;
    }

    public TransitionImpl<S, E, A> condition(ICondition<S, E> condition) {
        this.condition = condition;

        return this;
    }

    public TransitionImpl<S, E, A> action(IAction<A> action) {
        this.action = action;

        return this;
    }
}
