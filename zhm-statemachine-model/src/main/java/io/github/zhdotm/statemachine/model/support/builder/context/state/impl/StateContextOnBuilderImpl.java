package io.github.zhdotm.statemachine.model.support.builder.context.state.impl;

import io.github.zhdotm.statemachine.model.domain.impl.StateContextImpl;
import io.github.zhdotm.statemachine.model.support.builder.context.state.IStateContextOnBuilder;
import io.github.zhdotm.statemachine.model.support.builder.context.state.IStateContextToBuilder;
import lombok.AllArgsConstructor;

/**
 * @author zhihao.mao
 */

@AllArgsConstructor
public class StateContextOnBuilderImpl<S, E> implements IStateContextOnBuilder<S, E> {

    private final StateContextImpl<S, E> stateContext;

    public static <S, E> StateContextOnBuilderImpl<S, E> getInstance(StateContextImpl<S, E> stateContext) {

        return new StateContextOnBuilderImpl<>(stateContext);
    }

    @Override
    public IStateContextToBuilder<S, E> to(S stateId) {
        stateContext.to(stateId);

        return new StateContextToBuilderImpl<>(stateContext);
    }

}
