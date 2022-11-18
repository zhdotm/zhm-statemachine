package io.github.zhdotm.statemachine.support.builder.external.impl;

import io.github.zhdotm.statemachine.domain.ITransition;
import io.github.zhdotm.statemachine.support.builder.external.ExternalTransitionBuilder;
import io.github.zhdotm.statemachine.support.builder.external.ExternalTransitionFromBuilder;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhihao.mao
 */

public class ExternalTransitionBuilderImpl<S, E, A> implements ExternalTransitionBuilder<S, E, A> {

    private ITransition<S, E, A> transition;

    public static <S, E, A> ExternalTransitionBuilderImpl<S, E, A> getInstance(ITransition<S, E, A> transition) {

        ExternalTransitionBuilderImpl<S, E, A> transitionBuilder = new ExternalTransitionBuilderImpl<>();
        transitionBuilder.transition = transition;

        return transitionBuilder;
    }

    @Override
    public ExternalTransitionBuilder<S, E, A> sort(Integer sort) {
        transition.sort(sort);

        return this;
    }

    @Override
    public ExternalTransitionFromBuilder<S, E, A> from(S... stateIds) {
        transition.from(Arrays.asList(stateIds));

        return ExternalTransitionFromBuilderImpl.getInstance(transition);
    }

}
