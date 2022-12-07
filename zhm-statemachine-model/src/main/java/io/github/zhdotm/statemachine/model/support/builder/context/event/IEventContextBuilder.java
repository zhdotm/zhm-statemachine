package io.github.zhdotm.statemachine.model.support.builder.context.event;

/**
 * @author zhihao.mao
 */

public interface IEventContextBuilder<S, E> {

    IEventContextFromBuilder<S, E> from(S stateId);
}
