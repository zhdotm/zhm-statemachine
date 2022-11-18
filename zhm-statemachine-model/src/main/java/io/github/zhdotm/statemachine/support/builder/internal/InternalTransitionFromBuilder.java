package io.github.zhdotm.statemachine.support.builder.internal;

/**
 * @author zhihao.mao
 */

public interface InternalTransitionFromBuilder<S, E, A> {

    InternalTransitionOnBuilder<S, E, A> on(E eventId);

}
