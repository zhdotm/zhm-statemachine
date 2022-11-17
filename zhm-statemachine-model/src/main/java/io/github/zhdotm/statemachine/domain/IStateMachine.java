package io.github.zhdotm.statemachine.domain;


import java.util.Collection;

/**
 * 状态机
 *
 * @author zhihao.mao
 */

public interface IStateMachine<M, S, E, A> {

    /**
     * 获取状态机ID
     *
     * @return 状态机ID
     */
    M getStateMachineId();

    /**
     * 获取所有状态ID
     *
     * @return 状态ID
     */
    Collection<S> getStateIds();

    /**
     * 根据状态ID获取状态
     *
     * @param stateId 状态ID
     * @return 状态
     */
    IState<S, E> getState(S stateId);

    /**
     * 发布事件
     *
     * @param eventContext 事件上下文
     * @return 转换成功后的状态ID
     */
    S fireEvent(IEventContext<S, E> eventContext);
}
