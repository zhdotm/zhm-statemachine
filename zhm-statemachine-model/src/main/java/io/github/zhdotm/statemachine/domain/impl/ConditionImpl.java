package io.github.zhdotm.statemachine.domain.impl;

import io.github.zhdotm.statemachine.domain.ICondition;
import io.github.zhdotm.statemachine.domain.IEventContext;

import java.util.function.Function;

public class ConditionImpl<S, E> implements ICondition<S, E> {

    private Function<IEventContext<S, E>, Boolean> doCondition;

    public static <S, E> ConditionImpl<S, E> getInstance() {

        return new ConditionImpl<>();
    }

    public ConditionImpl<S, E> doCondition(Function<IEventContext<S, E>, Boolean> doCondition) {
        this.doCondition = doCondition;

        return this;
    }

    @Override
    public Boolean isSatisfied(IEventContext<S, E> eventContext) {

        return doCondition.apply(eventContext);
    }

}
