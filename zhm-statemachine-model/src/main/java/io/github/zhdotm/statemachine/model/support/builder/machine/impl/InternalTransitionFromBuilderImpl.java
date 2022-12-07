package io.github.zhdotm.statemachine.model.support.builder.machine.impl;

import io.github.zhdotm.statemachine.model.domain.ITransition;
import io.github.zhdotm.statemachine.model.support.builder.machine.IInternalTransitionFromBuilder;
import io.github.zhdotm.statemachine.model.support.builder.machine.IInternalTransitionOnBuilder;
import lombok.NonNull;

/**
 * @author zhihao.mao
 */

public class InternalTransitionFromBuilderImpl<S, E, C, A> implements IInternalTransitionFromBuilder<S, E, C, A> {

    private ITransition<S, E, C, A> transition;

    public static <S, E, C, A> InternalTransitionFromBuilderImpl<S, E, C, A> getInstance(@NonNull ITransition<S, E, C, A> transition) {

        InternalTransitionFromBuilderImpl<S, E, C, A> transitionFromBuilder = new InternalTransitionFromBuilderImpl<>();

        transitionFromBuilder.transition = transition;

        return transitionFromBuilder;
    }

    @Override
    public IInternalTransitionOnBuilder<S, E, C, A> on(@NonNull E eventId) {
        transition.on(eventId);

        return InternalTransitionOnBuilderImpl.getInstance(transition);
    }

}
