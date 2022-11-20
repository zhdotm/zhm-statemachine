package io.github.zhdotm.statemachine.model.domain;

/**
 * @author zhihao.mao
 */

public interface IStateContext<S, E> {

    /**
     * 获取状态ID
     *
     * @return 状态ID
     */
    S getStateId();

    /**
     * 获取结果负载
     *
     * @return 结果负载
     */
    <T> T getPayload();

    /**
     * 获取事件上下文
     *
     * @return 事件上下文
     */
    IEventContext<S, E> getEventContext();
}
