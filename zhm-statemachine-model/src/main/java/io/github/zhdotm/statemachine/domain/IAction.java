package io.github.zhdotm.statemachine.domain;


import io.github.zhdotm.statemachine.annotation.Action;

/**
 * 动作
 *
 * @author zhihao.mao
 */

public interface IAction {

    /**
     * 获取动作ID
     *
     * @return 动作ID
     */
    String getActionId();

    /**
     * 获取状态机ID
     *
     * @return 状态机ID
     */
    String getStateMachineId();

    /**
     * 获取状态机ID
     *
     * @return 状态机ID
     */
    default String getStateMachineIdWithAnnotation() {
        String stateMachineId = getStateMachineId();
        if (null != stateMachineId && !"".equalsIgnoreCase(stateMachineId)) {

            return stateMachineId;
        }
        Class<? extends IAction> clazz = this.getClass();
        Action action = clazz.getAnnotation(Action.class);

        return action == null ? null : action.stateMachineId();
    }

    /**
     * 执行动作
     *
     * @param args 参数
     * @return 是否执行成功
     */
    Boolean invoke(Object... args);

}
