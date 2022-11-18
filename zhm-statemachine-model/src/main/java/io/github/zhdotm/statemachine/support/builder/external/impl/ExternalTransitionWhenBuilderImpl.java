package io.github.zhdotm.statemachine.support.builder.external.impl;

import io.github.zhdotm.statemachine.domain.ITransition;
import io.github.zhdotm.statemachine.domain.impl.ActionImpl;
import io.github.zhdotm.statemachine.support.builder.external.ExternalTransitionToBuilder;
import io.github.zhdotm.statemachine.support.builder.external.ExternalTransitionWhenBuilder;

import java.util.function.Function;

/**
 * @author zhihao.mao
 */

public class ExternalTransitionWhenBuilderImpl<S, E, A> implements ExternalTransitionWhenBuilder<S, E, A> {

    private ITransition<S, E, A> transition;

    public static <S, E, A> ExternalTransitionWhenBuilderImpl<S, E, A> getInstance(ITransition<S, E, A> transition) {
        ExternalTransitionWhenBuilderImpl<S, E, A> transitionWhenBuilder = new ExternalTransitionWhenBuilderImpl<>();
        transitionWhenBuilder.transition = transition;

        return transitionWhenBuilder;
    }

    @Override
    public ExternalTransitionToBuilder<S, E, A> perform(A actionId, Function<Object[], Boolean> execute) {
        ActionImpl<A> action = ActionImpl.getInstance();
        action.actionId(actionId)
                .execute(execute);

        transition.perform(action);

        return ExternalTransitionToBuilderImpl.getInstance(transition);
    }

}
