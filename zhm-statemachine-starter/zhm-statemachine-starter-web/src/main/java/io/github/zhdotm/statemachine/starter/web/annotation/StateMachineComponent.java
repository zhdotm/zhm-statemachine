package io.github.zhdotm.statemachine.starter.web.annotation;

import io.github.zhdotm.statemachine.model.constant.TransitionTypeEnum;

import java.lang.annotation.*;

/**
 * 状态机组件
 *
 * @author zhihao.mao
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface StateMachineComponent {

    String stateMachineId();

    TransitionTypeEnum type();

    String[] from();

    String on();

    String to() default "";

    int sort() default Integer.MAX_VALUE;
}
