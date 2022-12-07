package io.github.zhdotm.statemachine.model.support.builder.context.state;

import io.github.zhdotm.statemachine.model.domain.IEventContext;

/**
 * @author zhihao.mao
 */

public interface IStateContextBuilder<S, E> {

    IStateContextOnBuilder<S, E> on(IEventContext<S, E> eventContext);
}
