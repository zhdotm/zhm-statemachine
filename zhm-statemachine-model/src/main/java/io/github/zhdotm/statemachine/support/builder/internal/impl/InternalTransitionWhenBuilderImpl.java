package io.github.zhdotm.statemachine.support.builder.internal.impl;

import io.github.zhdotm.statemachine.domain.ITransition;
import io.github.zhdotm.statemachine.domain.impl.ActionImpl;
import io.github.zhdotm.statemachine.support.builder.internal.InternalTransitionWhenBuilder;

import java.util.function.Function;

/**
 * @author zhihao.mao
 */

public class InternalTransitionWhenBuilderImpl<S, E, A> implements InternalTransitionWhenBuilder<S, E, A> {

    private ITransition<S, E, A> transition;

    public static <S, E, A> InternalTransitionWhenBuilderImpl<S, E, A> getInstance(ITransition<S, E, A> transition) {
        InternalTransitionWhenBuilderImpl<S, E, A> transitionWhenBuilder = new InternalTransitionWhenBuilderImpl<>();
        transitionWhenBuilder.transition = transition;

        return transitionWhenBuilder;
    }

    @Override
    public void perform(A actionId, Function<Object[], Boolean> execute) {
        ActionImpl<A> action = ActionImpl.getInstance();
        action.actionId(actionId)
                .execute(execute);

        transition.perform(action);
    }

}
