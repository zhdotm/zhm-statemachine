package io.github.zhdotm.statemachine.support.builder.internal.impl;

import io.github.zhdotm.statemachine.domain.ITransition;
import io.github.zhdotm.statemachine.support.builder.internal.InternalTransitionFromBuilder;
import io.github.zhdotm.statemachine.support.builder.internal.InternalTransitionOnBuilder;

/**
 * @author zhihao.mao
 */

public class InternalTransitionFromBuilderImpl<S, E, A> implements InternalTransitionFromBuilder<S, E, A> {

    private ITransition<S, E, A> transition;

    public static <S, E, A> InternalTransitionFromBuilderImpl<S, E, A> getInstance(ITransition<S, E, A> transition) {

        InternalTransitionFromBuilderImpl<S, E, A> transitionFromBuilder = new InternalTransitionFromBuilderImpl<>();

        transitionFromBuilder.transition = transition;

        return transitionFromBuilder;
    }

    @Override
    public InternalTransitionOnBuilder<S, E, A> on(E eventId) {
        transition.on(eventId);

        return InternalTransitionOnBuilderImpl.getInstance(transition);
    }

}
