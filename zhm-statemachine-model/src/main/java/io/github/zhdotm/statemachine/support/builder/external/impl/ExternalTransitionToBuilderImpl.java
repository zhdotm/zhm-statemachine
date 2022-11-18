package io.github.zhdotm.statemachine.support.builder.external.impl;

import io.github.zhdotm.statemachine.domain.ITransition;
import io.github.zhdotm.statemachine.support.builder.external.ExternalTransitionToBuilder;

/**
 * @author zhihao.mao
 */

public class ExternalTransitionToBuilderImpl<S, E, A> implements ExternalTransitionToBuilder<S, E, A> {

    private ITransition<S, E, A> transition;

    public static <S, E, A> ExternalTransitionToBuilderImpl<S, E, A> getInstance(ITransition<S, E, A> transition) {

        ExternalTransitionToBuilderImpl<S, E, A> toBuilder = new ExternalTransitionToBuilderImpl<>();
        toBuilder.transition = transition;

        return toBuilder;
    }

    @Override
    public void to(S stateId) {
        transition.to(stateId);
    }

}
