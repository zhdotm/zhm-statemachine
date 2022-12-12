package io.github.zhdotm.statemachine.model.support.builder.machine;

import io.github.zhdotm.statemachine.model.domain.ITransition;

/**
 * @author zhihao.mao
 */

public interface IInternalTransitionPerformBuilder<S, E, C, A> {

    ITransition<S, E, C, A> build();
}
