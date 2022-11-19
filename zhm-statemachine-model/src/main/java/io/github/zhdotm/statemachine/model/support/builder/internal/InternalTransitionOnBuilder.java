package io.github.zhdotm.statemachine.model.support.builder.internal;

import io.github.zhdotm.statemachine.model.domain.IEventContext;

import java.util.function.Function;

/**
 * @author zhihao.mao
 */

public interface InternalTransitionOnBuilder<S, E, C, A> {

    InternalTransitionWhenBuilder<S, E, C, A> when(C conditionId, Function<IEventContext<S, E>, Boolean> check);

}
