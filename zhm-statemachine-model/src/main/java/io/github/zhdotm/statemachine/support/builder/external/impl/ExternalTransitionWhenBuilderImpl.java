package io.github.zhdotm.statemachine.support.builder.external.impl;

import io.github.zhdotm.statemachine.domain.ITransition;
import io.github.zhdotm.statemachine.domain.impl.ActionImpl;
import io.github.zhdotm.statemachine.support.builder.external.ExternalTransitionToBuilder;
import io.github.zhdotm.statemachine.support.builder.external.ExternalTransitionWhenBuilder;

import java.util.function.Function;

/**
 * @author zhihao.mao
 */

public class ExternalTransitionWhenBuilderImpl<S, E, C, A> implements ExternalTransitionWhenBuilder<S, E, C, A> {

    private ITransition<S, E, C, A> transition;

    public static <S, E, C, A> ExternalTransitionWhenBuilderImpl<S, E, C, A> getInstance(ITransition<S, E, C, A> transition) {
        ExternalTransitionWhenBuilderImpl<S, E, C, A> transitionWhenBuilder = new ExternalTransitionWhenBuilderImpl<>();
        transitionWhenBuilder.transition = transition;

        return transitionWhenBuilder;
    }

    @Override
    public ExternalTransitionToBuilder<S, E, C, A> perform(A actionId, Function<Object[], Object> execute) {
        ActionImpl<A> action = ActionImpl.getInstance();
        action.actionId(actionId)
                .execute(execute);

        transition.perform(action);

        return ExternalTransitionToBuilderImpl.getInstance(transition);
    }

}
