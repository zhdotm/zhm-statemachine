package io.github.zhdotm.statemachine.domain;


import io.github.zhdotm.statemachine.annotation.Condition;

/**
 * 条件
 *
 * @author zhihao.mao
 */

public interface ICondition {

    /**
     * 获取条件ID
     *
     * @return 条件ID
     */
    String getConditionId();

    /**
     * 获取状态机ID
     *
     * @return 状态机ID
     */
    default String getStateMachineId() {
        Class<? extends ICondition> clazz = this.getClass();
        Condition condition = clazz.getAnnotation(Condition.class);

        return condition == null ? null : condition.stateMachineId();
    }

    /**
     * 是否满足条件
     *
     * @param event 事件
     * @return 出参
     */
    Boolean isSatisfied(IEvent event);

}
