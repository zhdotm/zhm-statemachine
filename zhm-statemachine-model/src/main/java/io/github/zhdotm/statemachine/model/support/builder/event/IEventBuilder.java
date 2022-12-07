package io.github.zhdotm.statemachine.model.support.builder.event;

/**
 * @author zhihao.mao
 */

public interface IEventBuilder<E> {

    IEventPayloadBuilder<E> payload(Object... objs);
}
