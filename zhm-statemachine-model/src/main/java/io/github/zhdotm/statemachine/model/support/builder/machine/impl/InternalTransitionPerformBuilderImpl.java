package io.github.zhdotm.statemachine.model.support.builder.machine.impl;

import io.github.zhdotm.statemachine.model.domain.ITransition;
import io.github.zhdotm.statemachine.model.support.builder.machine.IInternalTransitionPerformBuilder;
import lombok.NonNull;

/**
 * @author zhihao.mao
 */

public class InternalTransitionPerformBuilderImpl<S, E, C, A> implements IInternalTransitionPerformBuilder<S, E, C, A> {

    private ITransition<S, E, C, A> transition;

    public static <S, E, C, A> InternalTransitionPerformBuilderImpl<S, E, C, A> getInstance(@NonNull ITransition<S, E, C, A> transition) {
        InternalTransitionPerformBuilderImpl<S, E, C, A> transitionPerformBuilder = new InternalTransitionPerformBuilderImpl<>();
        transitionPerformBuilder.transition = transition;

        return transitionPerformBuilder;
    }

    @Override
    public ITransition<S, E, C, A> build() {

        return transition;
    }

}
