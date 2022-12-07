package io.github.zhdotm.statemachine.model.support.builder.machine;

/**
 * @author zhihao.mao
 */

public interface IInternalTransitionFromBuilder<S, E, C, A> {

    IInternalTransitionOnBuilder<S, E, C, A> on(E eventId);

}
