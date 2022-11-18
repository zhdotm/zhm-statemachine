package io.github.zhdotm.statemachine.support.builder.external.impl;

import io.github.zhdotm.statemachine.domain.IEventContext;
import io.github.zhdotm.statemachine.domain.ITransition;
import io.github.zhdotm.statemachine.domain.impl.ConditionImpl;
import io.github.zhdotm.statemachine.support.builder.external.ExternalTransitionOnBuilder;
import io.github.zhdotm.statemachine.support.builder.external.ExternalTransitionWhenBuilder;

import java.util.function.Function;

/**
 * @author zhihao.mao
 */

public class ExternalTransitionOnBuilderImpl<S, E, A> implements ExternalTransitionOnBuilder<S, E, A> {

    private ITransition<S, E, A> transition;

    public static <S, E, A> ExternalTransitionOnBuilderImpl<S, E, A> getInstance(ITransition<S, E, A> transition) {
        ExternalTransitionOnBuilderImpl<S, E, A> transitionOnBuilder = new ExternalTransitionOnBuilderImpl<>();
        transitionOnBuilder.transition = transition;

        return transitionOnBuilder;
    }

    @Override
    public ExternalTransitionWhenBuilder<S, E, A> when(Function<IEventContext<S, E>, Boolean> check) {
        ConditionImpl<S, E> condition = ConditionImpl.getInstance();
        condition.check(check);
        transition.when(condition);

        return ExternalTransitionWhenBuilderImpl.getInstance(transition);
    }

}
