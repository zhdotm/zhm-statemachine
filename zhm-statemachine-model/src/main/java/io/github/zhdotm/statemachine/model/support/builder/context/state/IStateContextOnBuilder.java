package io.github.zhdotm.statemachine.model.support.builder.context.state;

/**
 * @author zhihao.mao
 */

public interface IStateContextOnBuilder<S, E> {

    IStateContextToBuilder<S, E> to(S stateId);
}
