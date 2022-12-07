package io.github.zhdotm.statemachine.model.support.builder.machine;

/**
 * @author zhihao.mao
 */

public interface IExternalTransitionToBuilder<S, E, C, A> {

    void to(S stateId);
}
