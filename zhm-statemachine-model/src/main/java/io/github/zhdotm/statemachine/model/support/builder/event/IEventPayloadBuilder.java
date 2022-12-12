package io.github.zhdotm.statemachine.model.support.builder.event;

/**
 * @author zhihao.mao
 */

public interface IEventPayloadBuilder<E> {

    IEventIdBuilder<E> id(E eventId);
}
