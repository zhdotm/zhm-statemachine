package io.github.zhdotm.statemachine.support.builder.external;

/**
 * @author zhihao.mao
 */

public interface ExternalTransitionToBuilder<S, E, A> {

    void to(S stateId);
}
