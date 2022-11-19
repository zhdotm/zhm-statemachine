package io.github.zhdotm.statemachine.model.support.builder.internal;


/**
 * @author zhihao.mao
 */

public interface InternalTransitionBuilder<S, E, C, A> {

    InternalTransitionBuilder<S, E, C, A> sort(Integer sort);

    InternalTransitionFromBuilder<S, E, C, A> from(S... stateIds);
}
