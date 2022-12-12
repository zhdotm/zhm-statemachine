package io.github.zhdotm.statemachine.model.support.builder.context.state.impl;

import io.github.zhdotm.statemachine.model.domain.IStateContext;
import io.github.zhdotm.statemachine.model.domain.impl.StateContextImpl;
import io.github.zhdotm.statemachine.model.support.builder.context.state.IStateRetContext;
import lombok.AllArgsConstructor;

/**
 * @author zhihao.mao
 */

@AllArgsConstructor
public class StateRetContextImpl<S, E> implements IStateRetContext<S, E> {

    private final StateContextImpl<S, E> stateContext;

    public static <S, E> StateRetContextImpl<S, E> getInstance(StateContextImpl<S, E> stateContext) {

        return new StateRetContextImpl<>(stateContext);
    }

    @Override
    public IStateContext<S, E> build() {

        return stateContext;
    }

}
