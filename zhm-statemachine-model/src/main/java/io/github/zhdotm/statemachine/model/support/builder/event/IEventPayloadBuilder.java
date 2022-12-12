package io.github.zhdotm.statemachine.model.support.builder.event;

import io.github.zhdotm.statemachine.model.domain.IEvent;

/**
 * @author zhihao.mao
 */

public interface IEventPayloadBuilder<E> {

    IEvent<E> id(E eventId);
}
