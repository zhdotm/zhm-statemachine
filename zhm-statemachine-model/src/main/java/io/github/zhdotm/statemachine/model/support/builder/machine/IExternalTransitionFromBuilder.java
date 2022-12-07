package io.github.zhdotm.statemachine.model.support.builder.machine;

/**
 * @author zhihao.mao
 */

public interface IExternalTransitionFromBuilder<S, E, C, A> {

    IExternalTransitionOnBuilder<S, E, C, A> on(E eventId);

}
