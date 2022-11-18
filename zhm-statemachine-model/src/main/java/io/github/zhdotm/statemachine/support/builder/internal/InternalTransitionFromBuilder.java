package io.github.zhdotm.statemachine.support.builder.internal;

/**
 * @author zhihao.mao
 */

public interface InternalTransitionFromBuilder<S, E, C, A> {

    InternalTransitionOnBuilder<S, E, C, A> on(E eventId);

}
