package io.github.zhdotm.statemachine.domain;

/**
 * 事件上下文环境
 *
 * @author zhihao.mao
 */

public interface IEventContext {
    
    /**
     * 获取状态机ID
     *
     * @return 状态机ID
     */
    String getStateMachineId();

    /**
     * 获取状态ID
     *
     * @return 状态ID
     */
    String getStateId();

    /**
     * 获取事件
     *
     * @return 事件
     */
    IEvent getEvent();


}
