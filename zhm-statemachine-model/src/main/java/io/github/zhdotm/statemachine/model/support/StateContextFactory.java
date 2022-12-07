package io.github.zhdotm.statemachine.model.support;

import io.github.zhdotm.statemachine.model.support.builder.context.state.IStateContextBuilder;
import io.github.zhdotm.statemachine.model.support.builder.context.state.impl.StateContextBuilderImpl;

/**
 * @author zhihao.mao
 */

public class StateContextFactory {

    public static <S, E> IStateContextBuilder<S, E> create() {

        return StateContextBuilderImpl.getInstance();
    }
}
