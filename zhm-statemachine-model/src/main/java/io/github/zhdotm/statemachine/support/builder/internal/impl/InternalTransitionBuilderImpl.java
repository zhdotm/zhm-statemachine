package io.github.zhdotm.statemachine.support.builder.internal.impl;

import io.github.zhdotm.statemachine.domain.ITransition;
import io.github.zhdotm.statemachine.support.builder.internal.InternalTransitionBuilder;
import io.github.zhdotm.statemachine.support.builder.internal.InternalTransitionFromBuilder;
import lombok.NonNull;

import java.util.Arrays;

/**
 * @author zhihao.mao
 */

public class InternalTransitionBuilderImpl<S, E, C, A> implements InternalTransitionBuilder<S, E, C, A> {

    private ITransition<S, E, C, A> transition;

    public static <S, E, C, A> InternalTransitionBuilderImpl<S, E, C, A> getInstance(@NonNull ITransition<S, E, C, A> transition) {

        InternalTransitionBuilderImpl<S, E, C, A> transitionBuilder = new InternalTransitionBuilderImpl<>();
        transitionBuilder.transition = transition;

        return transitionBuilder;
    }

    @Override
    public InternalTransitionBuilder<S, E, C, A> sort(@NonNull Integer sort) {
        transition.sort(sort);

        return this;
    }

    @Override
    public InternalTransitionFromBuilder<S, E, C, A> from(@NonNull S... stateIds) {
        transition.from(Arrays.asList(stateIds));

        return InternalTransitionFromBuilderImpl.getInstance(transition);
    }

}
