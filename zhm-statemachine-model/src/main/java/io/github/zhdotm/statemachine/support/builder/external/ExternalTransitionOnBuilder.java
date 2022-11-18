package io.github.zhdotm.statemachine.support.builder.external;

import io.github.zhdotm.statemachine.domain.IEventContext;

import java.util.function.Function;

/**
 * @author zhihao.mao
 */

public interface ExternalTransitionOnBuilder<S, E, C, A> {

    ExternalTransitionWhenBuilder<S, E, C, A> when(C conditionId, Function<IEventContext<S, E>, Boolean> check);

}
