package io.github.zhdotm.statemachine.support.builder.external.impl;

import io.github.zhdotm.statemachine.domain.ITransition;
import io.github.zhdotm.statemachine.support.builder.external.ExternalTransitionFromBuilder;
import io.github.zhdotm.statemachine.support.builder.external.ExternalTransitionOnBuilder;

/**
 * @author zhihao.mao
 */

public class ExternalTransitionFromBuilderImpl<S, E, A> implements ExternalTransitionFromBuilder<S, E, A> {

    private ITransition<S, E, A> transition;

    public static <S, E, A> ExternalTransitionFromBuilderImpl<S, E, A> getInstance(ITransition<S, E, A> transition) {

        ExternalTransitionFromBuilderImpl<S, E, A> transitionFromBuilder = new ExternalTransitionFromBuilderImpl<>();

        transitionFromBuilder.transition = transition;

        return transitionFromBuilder;
    }

    @Override
    public ExternalTransitionOnBuilder<S, E, A> on(E eventId) {
        transition.on(eventId);

        return ExternalTransitionOnBuilderImpl.getInstance(transition);
    }

}
