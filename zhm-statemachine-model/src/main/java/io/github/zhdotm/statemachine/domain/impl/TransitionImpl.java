package io.github.zhdotm.statemachine.domain.impl;

import io.github.zhdotm.statemachine.constant.TransitionTypeEnum;
import io.github.zhdotm.statemachine.domain.IAction;
import io.github.zhdotm.statemachine.domain.ICondition;
import io.github.zhdotm.statemachine.domain.ITransition;
import lombok.Getter;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * @author zhihao.mao
 */

public class TransitionImpl<S, E, C, A> implements ITransition<S, E, C, A> {

    @Getter
    private final Collection<S> fromStateIds = new HashSet<>();
    @Getter
    private TransitionTypeEnum type;
    @Getter
    private Integer sort = Integer.MAX_VALUE;
    @Getter
    private S toStateId;

    @Getter
    private E eventId;

    @Getter
    private ICondition<S, E, C> condition;

    @Getter
    private IAction<A> action;

    public static <S, E, C, A> TransitionImpl<S, E, C, A> getInstance() {

        return new TransitionImpl<>();
    }

    @Override
    public ITransition<S, E, C, A> type(TransitionTypeEnum type) {
        this.type = type;

        return this;
    }

    @Override
    public ITransition<S, E, C, A> sort(Integer sort) {
        this.sort = sort;

        return this;
    }

    @Override
    public TransitionImpl<S, E, C, A> from(List<S> stateIds) {
        fromStateIds.addAll(stateIds);

        return this;
    }

    @Override
    public ITransition<S, E, C, A> on(E eventId) {
        this.eventId = eventId;

        return this;
    }

    @Override
    public ITransition<S, E, C, A> to(S stateId) {
        toStateId = stateId;

        return this;
    }

    @Override
    public TransitionImpl<S, E, C, A> when(ICondition<S, E, C> condition) {
        this.condition = condition;

        return this;
    }

    @Override
    public TransitionImpl<S, E, C, A> perform(IAction<A> action) {
        this.action = action;

        return this;
    }
}
