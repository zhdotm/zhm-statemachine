package io.github.zhdotm.statemachine.model.support.builder.external;

/**
 * @author zhihao.mao
 */

public interface ExternalTransitionToBuilder<S, E, C, A> {

    void to(S stateId);
}