package io.github.zhdotm.statemachine.domain.impl;

import io.github.zhdotm.statemachine.constant.TransitionTypeEnum;
import io.github.zhdotm.statemachine.domain.IAction;
import io.github.zhdotm.statemachine.domain.ICondition;
import io.github.zhdotm.statemachine.domain.ITransition;
import lombok.Getter;

import java.util.*;

/**
 * @author zhihao.mao
 */

public class TransitionImpl<S, E, A> implements ITransition<S, E, A> {

    @Getter
    private TransitionTypeEnum type;

    @Getter
    private Integer sort = Integer.MAX_VALUE;

    @Getter
    private final Collection<S> fromStateIds = new HashSet<>();

    @Getter
    private S toStateId;

    @Getter
    private E eventId;

    @Getter
    private ICondition<S, E> condition;

    @Getter
    private IAction<A> action;

    public static <S, E, A> TransitionImpl<S, E, A> getInstance() {

        return new TransitionImpl<>();
    }

    @Override
    public ITransition<S, E, A> type(TransitionTypeEnum type) {
        this.type = type;

        return this;
    }

    @Override
    public ITransition<S, E, A> sort(Integer sort) {
        this.sort = sort;

        return this;
    }

    @Override
    public TransitionImpl<S, E, A> from(List<S> stateIds) {
        fromStateIds.addAll(stateIds);

        return this;
    }

    @Override
    public ITransition<S, E, A> on(E eventId) {
        this.eventId = eventId;

        return this;
    }

    @Override
    public ITransition<S, E, A> to(S stateId) {
        toStateId = stateId;

        return this;
    }

    @Override
    public TransitionImpl<S, E, A> when(ICondition<S, E> condition) {
        this.condition = condition;

        return this;
    }

    @Override
    public TransitionImpl<S, E, A> perform(IAction<A> action) {
        this.action = action;

        return this;
    }
}
