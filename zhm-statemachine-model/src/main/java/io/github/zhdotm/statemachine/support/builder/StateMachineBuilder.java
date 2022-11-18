package io.github.zhdotm.statemachine.support.builder;


import io.github.zhdotm.statemachine.domain.IStateMachine;
import io.github.zhdotm.statemachine.support.builder.external.ExternalTransitionBuilder;
import io.github.zhdotm.statemachine.support.builder.internal.InternalTransitionBuilder;

/**
 * @author zhihao.mao
 */

public interface StateMachineBuilder<M, S, E, C, A> {

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
