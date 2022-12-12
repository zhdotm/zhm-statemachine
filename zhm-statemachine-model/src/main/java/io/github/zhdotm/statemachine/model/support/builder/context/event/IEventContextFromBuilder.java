package io.github.zhdotm.statemachine.model.support.builder.context.event;

import io.github.zhdotm.statemachine.model.domain.IEvent;

/**
 * @author zhihao.mao
 */
public interface IEventContextFromBuilder<S, E> {

    IEventOnContext<S, E> on(IEvent<E> event);
}
