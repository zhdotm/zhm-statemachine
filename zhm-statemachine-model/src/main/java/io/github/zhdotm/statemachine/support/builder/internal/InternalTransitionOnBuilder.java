package io.github.zhdotm.statemachine.support.builder.internal;

import io.github.zhdotm.statemachine.domain.IEventContext;

import java.util.function.Function;

/**
 * @author zhihao.mao
 */

public interface InternalTransitionOnBuilder<S, E, C, A> {

    InternalTransitionWhenBuilder<S, E, C, A> when(C conditionId, Function<IEventContext<S, E>, Boolean> check);

}
