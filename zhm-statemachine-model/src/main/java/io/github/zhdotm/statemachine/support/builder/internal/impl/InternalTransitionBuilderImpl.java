package io.github.zhdotm.statemachine.support.builder.internal.impl;

import io.github.zhdotm.statemachine.domain.ITransition;
import io.github.zhdotm.statemachine.support.builder.internal.InternalTransitionBuilder;
import io.github.zhdotm.statemachine.support.builder.internal.InternalTransitionFromBuilder;

import java.util.Arrays;

/**
 * @author zhihao.mao
 */

public class InternalTransitionBuilderImpl<S, E, A> implements InternalTransitionBuilder<S, E, A> {

    private ITransition<S, E, A> transition;

    public static <S, E, A> InternalTransitionBuilderImpl<S, E, A> getInstance(ITransition<S, E, A> transition) {

        InternalTransitionBuilderImpl<S, E, A> transitionBuilder = new InternalTransitionBuilderImpl<>();
        transitionBuilder.transition = transition;

        return transitionBuilder;
    }

    @Override
    public InternalTransitionBuilder<S, E, A> sort(Integer sort) {
        transition.sort(sort);

        return this;
    }

    @Override
    public InternalTransitionFromBuilder<S, E, A> from(S... stateIds) {
        transition.from(Arrays.asList(stateIds));

        return InternalTransitionFromBuilderImpl.getInstance(transition);
    }

}
