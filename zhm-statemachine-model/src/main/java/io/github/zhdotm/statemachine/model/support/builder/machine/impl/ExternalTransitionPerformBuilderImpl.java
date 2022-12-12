package io.github.zhdotm.statemachine.model.support.builder.machine.impl;

import io.github.zhdotm.statemachine.model.domain.ITransition;
import io.github.zhdotm.statemachine.model.support.builder.machine.IExternalTransitionPerformBuilder;
import lombok.NonNull;

/**
 * @author zhihao.mao
 */

public class ExternalTransitionPerformBuilderImpl<S, E, C, A> implements IExternalTransitionPerformBuilder<S, E, C, A> {

    private ITransition<S, E, C, A> transition;

    public static <S, E, C, A> ExternalTransitionPerformBuilderImpl<S, E, C, A> getInstance(ITransition<S, E, C, A> transition) {

        ExternalTransitionPerformBuilderImpl<S, E, C, A> toBuilder = new ExternalTransitionPerformBuilderImpl<>();
        toBuilder.transition = transition;

        return toBuilder;
    }

    @Override
    public ExternalTransitionToBuilderImpl<S, E, C, A> to(@NonNull S stateId) {
        transition.to(stateId);

        return ExternalTransitionToBuilderImpl.getInstance(transition);
    }

}
