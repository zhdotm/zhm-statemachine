package io.github.zhdotm.statemachine.support.builder.external;


/**
 * @author zhihao.mao
 */

public interface ExternalTransitionBuilder<S, E, A> {

    ExternalTransitionBuilder<S, E, A> sort(Integer sort);

    ExternalTransitionFromBuilder<S, E, A> from(S... stateIds);
}
