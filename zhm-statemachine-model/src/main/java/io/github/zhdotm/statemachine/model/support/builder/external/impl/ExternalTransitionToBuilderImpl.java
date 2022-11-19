package io.github.zhdotm.statemachine.model.support.builder.external.impl;

import io.github.zhdotm.statemachine.model.domain.ITransition;
import io.github.zhdotm.statemachine.model.support.builder.external.ExternalTransitionToBuilder;
import lombok.NonNull;

/**
 * @author zhihao.mao
 */

public class ExternalTransitionToBuilderImpl<S, E, C, A> implements ExternalTransitionToBuilder<S, E, C, A> {

    private ITransition<S, E, C, A> transition;

    public static <S, E, C, A> ExternalTransitionToBuilderImpl<S, E, C, A> getInstance(ITransition<S, E, C, A> transition) {

        ExternalTransitionToBuilderImpl<S, E, C, A> toBuilder = new ExternalTransitionToBuilderImpl<>();
        toBuilder.transition = transition;

        return toBuilder;
    }

    @Override
    public void to(@NonNull S stateId) {
        transition.to(stateId);
    }

}
