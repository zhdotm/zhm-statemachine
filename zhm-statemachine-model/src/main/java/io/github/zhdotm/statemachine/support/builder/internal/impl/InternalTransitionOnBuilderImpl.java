package io.github.zhdotm.statemachine.support.builder.internal.impl;

import io.github.zhdotm.statemachine.domain.IEventContext;
import io.github.zhdotm.statemachine.domain.ITransition;
import io.github.zhdotm.statemachine.domain.impl.ConditionImpl;
import io.github.zhdotm.statemachine.support.builder.internal.InternalTransitionOnBuilder;
import io.github.zhdotm.statemachine.support.builder.internal.InternalTransitionWhenBuilder;

import java.util.function.Function;

/**
 * @author zhihao.mao
 */

public class InternalTransitionOnBuilderImpl<S, E, C, A> implements InternalTransitionOnBuilder<S, E, C, A> {

    private ITransition<S, E, C, A> transition;

    public static <S, E, C, A> InternalTransitionOnBuilderImpl<S, E, C, A> getInstance(ITransition<S, E, C, A> transition) {
        InternalTransitionOnBuilderImpl<S, E, C, A> transitionOnBuilder = new InternalTransitionOnBuilderImpl<>();
        transitionOnBuilder.transition = transition;

        return transitionOnBuilder;
    }

    @Override
    public InternalTransitionWhenBuilder<S, E, C, A> when(C conditionId, Function<IEventContext<S, E>, Boolean> check) {
        ConditionImpl<S, E, C> condition = ConditionImpl.getInstance();
        condition.conditionId(conditionId)
                .check(check);
        transition.when(condition);

        return InternalTransitionWhenBuilderImpl.getInstance(transition);
    }

}
