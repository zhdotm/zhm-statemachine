package io.github.zhdotm.statemachine.model.support.builder.context.state;

/**
 * @author zhihao.mao
 */

public interface IStateContextToBuilder<S, E> {

    IStateRetContext<S, E> ret(Object obj);
}
