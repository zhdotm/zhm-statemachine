package io.github.zhdotm.statemachine.model.support.builder.context.state;

import io.github.zhdotm.statemachine.model.domain.IStateContext;

/**
 * @author zhihao.mao
 */

public interface IStateRetContext<S, E> {

    IStateContext<S, E> build();
}
