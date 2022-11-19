package io.github.zhdotm.statemachine.model.support.builder.external;

import io.github.zhdotm.statemachine.model.domain.IEventContext;

import java.util.function.Function;

/**
 * @author zhihao.mao
 */

public interface ExternalTransitionOnBuilder<S, E, C, A> {

    ExternalTransitionWhenBuilder<S, E, C, A> when(C conditionId, Function<IEventContext<S, E>, Boolean> check);

}
