package io.github.zhdotm.statemachine.model.support.builder;


import io.github.zhdotm.statemachine.model.domain.IStateMachine;
import io.github.zhdotm.statemachine.model.domain.ITransition;
import io.github.zhdotm.statemachine.model.support.builder.external.ExternalTransitionBuilder;
import io.github.zhdotm.statemachine.model.support.builder.internal.InternalTransitionBuilder;

import java.util.List;

/**
 * @author zhihao.mao
 */

public interface StateMachineBuilder<M, S, E, C, A> {

    /**
     * 添加转换
     *
     * @param transitions 转换
     */
    void transitions(List<ITransition<S, E, C, A>> transitions);

    /**
     * 创建外部转换构建器
     *
     * @return 外部转换构建器
     */
    ExternalTransitionBuilder<S, E, C, A> createExternalTransition();

    /**
     * 创建内部转换构建器
     *
     * @return 内部转换构建器
     */
    InternalTransitionBuilder<S, E, C, A> createInternalTransition();

    /**
     * 构件状态机
     *
     * @param stateMachineId 状态机ID
     * @return 状态机
     */
    IStateMachine<M, S, E, C, A> build(M stateMachineId);

}
