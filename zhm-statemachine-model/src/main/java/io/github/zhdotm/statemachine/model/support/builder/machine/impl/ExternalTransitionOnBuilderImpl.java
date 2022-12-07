package io.github.zhdotm.statemachine.model.support.builder.machine.impl;

import io.github.zhdotm.statemachine.model.domain.IEventContext;
import io.github.zhdotm.statemachine.model.domain.ITransition;
import io.github.zhdotm.statemachine.model.domain.impl.ConditionImpl;
import io.github.zhdotm.statemachine.model.support.builder.machine.IExternalTransitionOnBuilder;
import io.github.zhdotm.statemachine.model.support.builder.machine.IExternalTransitionWhenBuilder;
import lombok.NonNull;

import java.util.function.Function;

/**
 * @author zhihao.mao
 */

public class ExternalTransitionOnBuilderImpl<S, E, C, A> implements IExternalTransitionOnBuilder<S, E, C, A> {

    private ITransition<S, E, C, A> transition;

    public static <S, E, C, A> ExternalTransitionOnBuilderImpl<S, E, C, A> getInstance(ITransition<S, E, C, A> transition) {
        ExternalTransitionOnBuilderImpl<S, E, C, A> transitionOnBuilder = new ExternalTransitionOnBuilderImpl<>();
        transitionOnBuilder.transition = transition;

        return transitionOnBuilder;
    }

    @Override
    public IExternalTransitionWhenBuilder<S, E, C, A> when(@NonNull C conditionId, @NonNull Function<IEventContext<S, E>, Boolean> check) {
        ConditionImpl<S, E, C> condition = ConditionImpl.getInstance();
        condition.conditionId(conditionId)
                .check(check);
        transition.when(condition);

        return ExternalTransitionWhenBuilderImpl.getInstance(transition);
    }

}
