package io.github.zhdotm.statemachine.model.support.builder.machine.impl;

import io.github.zhdotm.statemachine.model.domain.ITransition;
import io.github.zhdotm.statemachine.model.domain.impl.ActionImpl;
import io.github.zhdotm.statemachine.model.support.builder.machine.IInternalTransitionPerformBuilder;
import io.github.zhdotm.statemachine.model.support.builder.machine.IInternalTransitionWhenBuilder;
import lombok.NonNull;

import java.util.function.Function;

/**
 * @author zhihao.mao
 */

public class InternalTransitionWhenBuilderImpl<S, E, C, A> implements IInternalTransitionWhenBuilder<S, E, C, A> {

    private ITransition<S, E, C, A> transition;

    public static <S, E, C, A> InternalTransitionWhenBuilderImpl<S, E, C, A> getInstance(@NonNull ITransition<S, E, C, A> transition) {
        InternalTransitionWhenBuilderImpl<S, E, C, A> transitionWhenBuilder = new InternalTransitionWhenBuilderImpl<>();
        transitionWhenBuilder.transition = transition;

        return transitionWhenBuilder;
    }

    @Override
    public IInternalTransitionPerformBuilder<S, E, C, A> perform(@NonNull A actionId, @NonNull Function<Object[], Object> execute) {
        ActionImpl<A> action = ActionImpl.getInstance();
        action.actionId(actionId)
                .execute(execute);

        transition.perform(action);

        return InternalTransitionPerformBuilderImpl.getInstance(transition);
    }

}
