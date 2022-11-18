package io.github.zhdotm.statemachine.support.builder.internal.impl;

import io.github.zhdotm.statemachine.domain.IEventContext;
import io.github.zhdotm.statemachine.domain.ITransition;
import io.github.zhdotm.statemachine.domain.impl.ConditionImpl;
import io.github.zhdotm.statemachine.support.builder.internal.InternalTransitionOnBuilder;
import io.github.zhdotm.statemachine.support.builder.internal.InternalTransitionWhenBuilder;

import java.util.function.Function;

/**
 * @author zhihao.mao
 */

public class InternalTransitionOnBuilderImpl<S, E, A> implements InternalTransitionOnBuilder<S, E, A> {

    private ITransition<S, E, A> transition;

    public static <S, E, A> InternalTransitionOnBuilderImpl<S, E, A> getInstance(ITransition<S, E, A> transition) {
        InternalTransitionOnBuilderImpl<S, E, A> transitionOnBuilder = new InternalTransitionOnBuilderImpl<>();
        transitionOnBuilder.transition = transition;

        return transitionOnBuilder;
    }

    @Override
    public InternalTransitionWhenBuilder<S, E, A> when(Function<IEventContext<S, E>, Boolean> check) {
        ConditionImpl<S, E> condition = ConditionImpl.getInstance();
        condition.doCondition(check);
        transition.when(condition);

        return InternalTransitionWhenBuilderImpl.getInstance(transition);
    }

}
