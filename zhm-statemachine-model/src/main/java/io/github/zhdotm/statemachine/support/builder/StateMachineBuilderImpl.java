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

public class StateMachineBuilderImpl<M, S, E> implements StateMachineBuilder<M, S, E> {

    @Override
    public IStateMachine<M, S, E> build(M stateMachineId) {

        return null;
    }

}
