package io.github.zhdotm.statemachine.support.builder.external.impl;

import io.github.zhdotm.statemachine.domain.IEventContext;
import io.github.zhdotm.statemachine.domain.ITransition;
import io.github.zhdotm.statemachine.domain.impl.ConditionImpl;
import io.github.zhdotm.statemachine.support.builder.external.ExternalTransitionOnBuilder;
import io.github.zhdotm.statemachine.support.builder.external.ExternalTransitionWhenBuilder;
import lombok.NonNull;

import java.util.function.Function;

/**
 * @author zhihao.mao
 */

public class ExternalTransitionOnBuilderImpl<S, E, C, A> implements ExternalTransitionOnBuilder<S, E, C, A> {

    private ITransition<S, E, C, A> transition;

    public static <S, E, C, A> ExternalTransitionOnBuilderImpl<S, E, C, A> getInstance(ITransition<S, E, C, A> transition) {
        ExternalTransitionOnBuilderImpl<S, E, C, A> transitionOnBuilder = new ExternalTransitionOnBuilderImpl<>();
        transitionOnBuilder.transition = transition;

        return transitionOnBuilder;
    }

    @Override
    public ExternalTransitionWhenBuilder<S, E, C, A> when(@NonNull C conditionId, @NonNull Function<IEventContext<S, E>, Boolean> check) {
        ConditionImpl<S, E, C> condition = ConditionImpl.getInstance();
        condition.conditionId(conditionId)
                .check(check);
        transition.when(condition);

        return ExternalTransitionWhenBuilderImpl.getInstance(transition);
    }

}
