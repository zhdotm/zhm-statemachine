package io.github.zhdotm.statemachine.support.builder.external.impl;

import io.github.zhdotm.statemachine.domain.ITransition;
import io.github.zhdotm.statemachine.support.builder.external.ExternalTransitionFromBuilder;
import io.github.zhdotm.statemachine.support.builder.external.ExternalTransitionOnBuilder;

/**
 * @author zhihao.mao
 */

public class ExternalTransitionFromBuilderImpl<S, E, C, A> implements ExternalTransitionFromBuilder<S, E, C, A> {

    private ITransition<S, E, C, A> transition;

    public static <S, E, C, A> ExternalTransitionFromBuilderImpl<S, E, C, A> getInstance(ITransition<S, E, C, A> transition) {

        ExternalTransitionFromBuilderImpl<S, E, C, A> transitionFromBuilder = new ExternalTransitionFromBuilderImpl<>();

        transitionFromBuilder.transition = transition;

        return transitionFromBuilder;
    }

    @Override
    public ExternalTransitionOnBuilder<S, E, C, A> on(E eventId) {
        transition.on(eventId);

        return ExternalTransitionOnBuilderImpl.getInstance(transition);
    }

}
