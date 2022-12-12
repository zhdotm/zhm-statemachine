package io.github.zhdotm.statemachine.model.support.builder.context.event;

import io.github.zhdotm.statemachine.model.domain.IEventContext;

/**
 * @author zhihao.mao
 */

public interface IEventOnContext<S, E> {

    IEventContext<S, E> build();
}
