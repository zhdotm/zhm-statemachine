package io.github.zhdotm.statemachine.support.builder;


import io.github.zhdotm.statemachine.domain.IStateMachine;

/**
 * 状态机构造器
 *
 * @param <M> 状态机ID类型
 * @param <S> 状态ID类型
 * @param <E> 事件ID类型
 * @author zhihao.mao
 */

public interface StateMachineBuilder<M, S, E> {

    /**
     * 构件状态机
     *
     * @param stateMachineId 状态机ID
     * @return 状态机
     */
    IStateMachine<M, S, E> build(M stateMachineId);

}
