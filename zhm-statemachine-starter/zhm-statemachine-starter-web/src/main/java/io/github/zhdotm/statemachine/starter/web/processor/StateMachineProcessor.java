package io.github.zhdotm.statemachine.starter.web.processor;

import io.github.zhdotm.statemachine.model.constant.CharacterEnum;
import io.github.zhdotm.statemachine.model.constant.TransitionTypeEnum;
import io.github.zhdotm.statemachine.model.exception.StateMachineException;
import io.github.zhdotm.statemachine.model.exception.util.ExceptionUtil;
import io.github.zhdotm.statemachine.starter.web.adapter.ITransitionAdapter;
import io.github.zhdotm.statemachine.starter.web.annotation.StateMachineComponent;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;

/**
 * @author zhihao.mao
 */

public class StateMachineProcessor implements SmartInstantiationAwareBeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        StateMachineComponent stateMachineComponent = bean
                .getClass()
                .getDeclaredAnnotation(StateMachineComponent.class);

        if (stateMachineComponent != null) {

            if (stateMachineComponent.type() == TransitionTypeEnum.EXTERNAL) {

                ExceptionUtil.isTrue(!CharacterEnum.EMPTY.getValue().equalsIgnoreCase(stateMachineComponent.to()), StateMachineException.class, "构建[%s]Bean失败: 外部转换必须指定转换后状态", beanName);
            }

            ExceptionUtil.isTrue(bean instanceof ITransitionAdapter, StateMachineException.class, "构建[%s]Bean失败: 携带@StateMachineComponent注解的Bean必须实现TransitionAdapter接口", beanName);
        }

        return bean;
    }

}
