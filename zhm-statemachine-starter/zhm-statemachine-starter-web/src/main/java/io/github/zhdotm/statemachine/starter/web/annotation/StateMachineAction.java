package io.github.zhdotm.statemachine.starter.web.annotation;

import java.lang.annotation.*;

/**
 * 状态机动作组件
 *
 * @author zhihao.mao
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface StateMachineAction {

    String acitonId();
}
