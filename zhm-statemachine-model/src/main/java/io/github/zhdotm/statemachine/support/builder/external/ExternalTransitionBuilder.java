package io.github.zhdotm.statemachine.support.builder.external;


/**
 * @author zhihao.mao
 */

public interface ExternalTransitionBuilder<S, E, C, A> {

    ExternalTransitionBuilder<S, E, C, A> sort(Integer sort);

    ExternalTransitionFromBuilder<S, E, C, A> from(S... stateIds);
}
