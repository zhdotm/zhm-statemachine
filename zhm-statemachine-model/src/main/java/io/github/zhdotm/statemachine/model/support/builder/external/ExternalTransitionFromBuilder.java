package io.github.zhdotm.statemachine.model.support.builder.external;

/**
 * @author zhihao.mao
 */

public interface ExternalTransitionFromBuilder<S, E, C, A> {

    ExternalTransitionOnBuilder<S, E, C, A> on(E eventId);

}
