package io.github.zhdotm.statemachine.model.support.builder.context.event;

import io.github.zhdotm.statemachine.model.domain.IEvent;
import io.github.zhdotm.statemachine.model.domain.IEventContext;

/**
 * @author zhihao.mao
 */
public interface IEventContextFromBuilder<S, E> {

    IEventContext<S, E> on(IEvent<E> event);
}
