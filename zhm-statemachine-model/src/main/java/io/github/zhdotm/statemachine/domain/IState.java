package io.github.zhdotm.statemachine.domain;


import java.util.Collection;

/**
 * @author zhihao.mao
 */

public interface IState<S, E> {

    /**
     * 获取状态唯一ID
     *
     * @return 状态唯一ID
     */
    S getStateId();

    /**
     * 设置状态ID
     *
     * @param stateId 状态ID
     * @return 状态
     */
    IState<S, E> stateId(S stateId);

    /**
     * 添加事件ID
     *
     * @param eventId 事件ID
     * @return 状态
     */
    IState<S, E> addEventId(E eventId);

    /**
     * 获取所有事件ID
     *
     * @return 事件ID
     */
    Collection<E> getEventIds();
}
