package io.github.zhdotm.statemachine.model;


import io.github.zhdotm.statemachine.constant.EventTypeEnum;

/**
 * 事件
 *
 * @author zhihao.mao
 */

public interface IEvent {

    /**
     * 获取事件类型(默认常规类型)
     *
     * @return 事件类型
     */
    default EventTypeEnum getType() {

        return EventTypeEnum.NORMAL;
    }

    /**
     * 获取事件ID
     *
     * @return 事件ID
     */
    String getEventId();

    /**
     * 获取参数负载
     *
     * @return 参数负载
     */
    Object[] getPayload();

}
