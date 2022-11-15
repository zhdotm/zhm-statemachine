package io.github.zhdotm.statemachine.annotation;

import java.lang.annotation.*;

/**
 * 动作注解: 配合IAction, 声明一个动作
 *
 * @author zhihao.mao
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Action {

    /**
     * 所属状态机ID
     *
     * @return 状态机ID
     */
    String stateMachineId();

}
