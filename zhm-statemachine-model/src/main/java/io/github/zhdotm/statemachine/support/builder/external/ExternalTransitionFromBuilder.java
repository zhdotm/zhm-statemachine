package io.github.zhdotm.statemachine.support.builder.external;

/**
 * @author zhihao.mao
 */

public interface ExternalTransitionFromBuilder<S, E, A> {

    ExternalTransitionOnBuilder<S, E, A> on(E eventId);

}
