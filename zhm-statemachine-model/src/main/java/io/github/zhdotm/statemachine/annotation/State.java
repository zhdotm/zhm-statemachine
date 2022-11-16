package io.github.zhdotm.statemachine.annotation;


import io.github.zhdotm.statemachine.constant.StateTypeEnum;

import java.lang.annotation.*;

/**
 * 注意: 同个状态机, stateId唯一
 * 状态注解: 配合IState, 声明一个状态
 *
 * @author zhihao.mao
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface State {

    /**
     * 所属状态机ID
     *
     * @return 状态机ID
     */
    String stateMachineId();

    /**
     * 状态类型
     *
     * @return 状态类型
     */
    StateTypeEnum type();

    /**
     * 下一个状态机ID
     *
     * @return 状态机ID
     */
    String nextStateMachineId();
}
