package io.github.zhdotm.statemachine.annotation;



import io.github.zhdotm.statemachine.constant.TransitionTypeEnum;

import java.lang.annotation.*;

/**
 * 转换注解: 配合ITransition, 声明一个转换
 *
 * @author zhihao.mao
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Transition {
    /**
     * 所属状态机ID
     *
     * @return 状态机ID
     */
    String stateMachineId();

    /**
     * 排序，默认最后
     *
     * @return 排序
     */
    int sortId() default Integer.MAX_VALUE;

    /**
     * 转换类型
     *
     * @return 转换类型
     */
    TransitionTypeEnum type();

    /**
     * 来源状态ID
     *
     * @return 状态ID
     */
    String stateIdFrom();

    /**
     * 去向状态ID
     *
     * @return 状态ID
     */
    String stateIdTo();

    /**
     * 应用条件ID
     *
     * @return 条件ID
     */
    String conditionId();

    /**
     * 动作ID
     *
     * @return 动作ID
     */
    String actionId();

}
