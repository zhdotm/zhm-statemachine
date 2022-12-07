package io.github.zhdotm.statemachine.model.support.builder.context.state.impl;

import io.github.zhdotm.statemachine.model.domain.IStateContext;
import io.github.zhdotm.statemachine.model.domain.impl.StateContextImpl;
import io.github.zhdotm.statemachine.model.support.builder.context.state.IStateContextToBuilder;
import lombok.AllArgsConstructor;

/**
 * @author zhihao.mao
 */

@AllArgsConstructor
public class StateContextToBuilderImpl<S, E> implements IStateContextToBuilder<S, E> {

    private final StateContextImpl<S, E> stateContext;

    public static <S, E> StateContextToBuilderImpl<S, E> getInstance(StateContextImpl<S, E> stateContext) {

        return new StateContextToBuilderImpl<>(stateContext);
    }

    @Override
    public IStateContext<S, E> ret(Object obj) {

        return stateContext.ret(obj);
    }

}
