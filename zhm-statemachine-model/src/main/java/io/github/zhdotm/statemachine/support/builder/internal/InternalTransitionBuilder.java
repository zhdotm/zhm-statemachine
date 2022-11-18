package io.github.zhdotm.statemachine.support.builder.internal;


/**
 * @author zhihao.mao
 */

public interface InternalTransitionBuilder<S, E, A> {

    InternalTransitionBuilder<S, E, A> sort(Integer sort);

    InternalTransitionFromBuilder<S, E, A> from(S... stateIds);
}