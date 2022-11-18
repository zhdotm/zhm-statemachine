package io.github.zhdotm.statemachine.domain;


/**
 * @author zhihao.mao
 */

public interface IEvent<E> {

    /**
     * 获取事件ID
     *
     * @return 事件ID
     */
    E getEventId();

    /**
     * 获取参数负载
     *
     * @return 参数负载
     */
    Object[] getPayload();

}
