package io.github.zhdotm.statemachine.domain;

/**
 * 事件上下文
 *
 * @author zhihao.mao
 */

public interface IEventContext<S, E> {

    /**
     * 获取状态ID
     *
     * @return 状态ID
     */
    S getStateId();

    /**
     * 获取事件
     *
     * @return 事件
     */
    IEvent<E> getEvent();


}
