package io.github.zhdotm.statemachine.model.support.builder.machine.impl;

import io.github.zhdotm.statemachine.model.domain.ITransition;
import io.github.zhdotm.statemachine.model.support.builder.machine.IExternalTransitionFromBuilder;
import io.github.zhdotm.statemachine.model.support.builder.machine.IExternalTransitionOnBuilder;
import lombok.NonNull;

/**
 * @author zhihao.mao
 */

public class ExternalTransitionFromBuilderImpl<S, E, C, A> implements IExternalTransitionFromBuilder<S, E, C, A> {

    private ITransition<S, E, C, A> transition;

    public static <S, E, C, A> ExternalTransitionFromBuilderImpl<S, E, C, A> getInstance(ITransition<S, E, C, A> transition) {

        ExternalTransitionFromBuilderImpl<S, E, C, A> transitionFromBuilder = new ExternalTransitionFromBuilderImpl<>();

        transitionFromBuilder.transition = transition;

        return transitionFromBuilder;
    }

    @Override
    public IExternalTransitionOnBuilder<S, E, C, A> on(@NonNull E eventId) {
        transition.on(eventId);

        return ExternalTransitionOnBuilderImpl.getInstance(transition);
    }

}
