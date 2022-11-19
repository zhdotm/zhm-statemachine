package io.github.zhdotm.statemachine.support.builder.external.impl;

import io.github.zhdotm.statemachine.domain.ITransition;
import io.github.zhdotm.statemachine.support.builder.external.ExternalTransitionBuilder;
import io.github.zhdotm.statemachine.support.builder.external.ExternalTransitionFromBuilder;
import lombok.NonNull;

import java.util.Arrays;

/**
 * @author zhihao.mao
 */

public class ExternalTransitionBuilderImpl<S, E, C, A> implements ExternalTransitionBuilder<S, E, C, A> {

    private ITransition<S, E, C, A> transition;

    public static <S, E, C, A> ExternalTransitionBuilderImpl<S, E, C, A> getInstance(ITransition<S, E, C, A> transition) {

        ExternalTransitionBuilderImpl<S, E, C, A> transitionBuilder = new ExternalTransitionBuilderImpl<>();
        transitionBuilder.transition = transition;

        return transitionBuilder;
    }

    @Override
    public ExternalTransitionBuilder<S, E, C, A> sort(@NonNull Integer sort) {
        transition.sort(sort);

        return this;
    }

    @Override
    public ExternalTransitionFromBuilder<S, E, C, A> from(@NonNull S... stateIds) {
        transition.from(Arrays.asList(stateIds));

        return ExternalTransitionFromBuilderImpl.getInstance(transition);
    }

}
