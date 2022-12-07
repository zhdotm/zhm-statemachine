package io.github.zhdotm.statemachine.model.support.builder.machine.impl;

import io.github.zhdotm.statemachine.model.domain.ITransition;
import io.github.zhdotm.statemachine.model.support.builder.machine.IInternalTransitionBuilder;
import io.github.zhdotm.statemachine.model.support.builder.machine.IInternalTransitionFromBuilder;
import lombok.NonNull;

import java.util.Arrays;

/**
 * @author zhihao.mao
 */

public class InternalTransitionBuilderImpl<S, E, C, A> implements IInternalTransitionBuilder<S, E, C, A> {

    private ITransition<S, E, C, A> transition;

    public static <S, E, C, A> InternalTransitionBuilderImpl<S, E, C, A> getInstance(@NonNull ITransition<S, E, C, A> transition) {

        InternalTransitionBuilderImpl<S, E, C, A> transitionBuilder = new InternalTransitionBuilderImpl<>();
        transitionBuilder.transition = transition;

        return transitionBuilder;
    }

    @Override
    public IInternalTransitionBuilder<S, E, C, A> sort(@NonNull Integer sort) {
        transition.sort(sort);

        return this;
    }

    @Override
    public IInternalTransitionFromBuilder<S, E, C, A> from(@NonNull S... stateIds) {
        transition.from(Arrays.asList(stateIds));

        return InternalTransitionFromBuilderImpl.getInstance(transition);
    }

}
