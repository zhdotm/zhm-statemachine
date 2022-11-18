package io.github.zhdotm.statemachine.support.builder.external;

import io.github.zhdotm.statemachine.domain.IEventContext;

import java.util.function.Function;

/**
 * @author zhihao.mao
 */

public interface ExternalTransitionOnBuilder<S, E, A> {

    ExternalTransitionWhenBuilder<S, E, A> when(Function<IEventContext<S, E>, Boolean> check);

}
