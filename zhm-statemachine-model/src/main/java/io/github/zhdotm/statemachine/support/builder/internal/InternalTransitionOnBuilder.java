package io.github.zhdotm.statemachine.support.builder.internal;

import io.github.zhdotm.statemachine.domain.IEventContext;

import java.util.function.Function;

/**
 * @author zhihao.mao
 */

public interface InternalTransitionOnBuilder<S, E, A> {

    InternalTransitionWhenBuilder<S, E, A> when(Function<IEventContext<S, E>, Boolean> check);

}
