package io.github.zhdotm.statemachine.domain;


import io.github.zhdotm.statemachine.annotation.State;
import io.github.zhdotm.statemachine.constant.StateTypeEnum;

/**
 * 状态
 *
 * @author zhihao.mao
 */

public interface IState {

    /**
     * 获取状态唯一ID
     *
     * @return 状态唯一ID
     */
    String getStateId();

    /**
     * 获取状态机ID
     *
     * @return 状态机ID
     */
    String getStateMachineId();

    /**
     * 状态类型
     *
     * @return 状态类型
     */
    StateTypeEnum getType();

    /**
     * 获取下一个状态机ID
     *
     * @return 状态机ID
     */
    String getNextStateMachineId();

    /**
     * 获取状态机ID
     *
     * @return 状态机ID
     */
    default String getStateMachineIdWithAnnotation() {
        String stateMachineId = getStateMachineId();
        if (stateMachineId != null && !"".equalsIgnoreCase(stateMachineId)) {

            return stateMachineId;
        }
        State state = getStateAnnotation();

        return state == null ? null : state.stateMachineId();
    }

    /**
     * 状态类型
     *
     * @return 状态类型
     */
    default StateTypeEnum getTypeWithAnnotation() {
        StateTypeEnum type = getType();
        if (type != null) {

            return type;
        }

        State state = getStateAnnotation();

        return state == null ? StateTypeEnum.NORMAL : state.type();
    }

    /**
     * 下个状态机ID(只有StateTypeEnum.BRIDGE类型允许跨状态机)
     *
     * @return 下个状态机ID
     */
    default String getNextStateMachineIdWithAnnotation() {
        String nextStateMachineId = getNextStateMachineId();
        if (nextStateMachineId != null && !"".equalsIgnoreCase(nextStateMachineId)) {

            return nextStateMachineId;
        }
        State state = getStateAnnotation();

        return state == null ? null : state.nextStateMachineId();
    }

    /**
     * 获取state注解
     *
     * @return state注解
     */
    default State getStateAnnotation() {
        Class<? extends IState> clazz = this.getClass();

        return clazz.getAnnotation(State.class);
    }

}
