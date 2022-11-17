package io.github.zhdotm.statemachine.domain;


import java.util.Collection;
import java.util.List;

/**
 * 状态
 *
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
     * 获取所有事件ID
     *
     * @return 事件ID
     */
    Collection<E> getEventIds();

}
