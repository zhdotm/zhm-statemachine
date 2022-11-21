package io.github.zhdotm.statemachine.starter.web.adapter;

import io.github.zhdotm.statemachine.model.constant.TransitionTypeEnum;
import io.github.zhdotm.statemachine.model.domain.IEvent;
import io.github.zhdotm.statemachine.model.domain.IEventContext;
import io.github.zhdotm.statemachine.model.domain.ITransition;
import io.github.zhdotm.statemachine.model.domain.impl.ActionImpl;
import io.github.zhdotm.statemachine.model.domain.impl.ConditionImpl;
import io.github.zhdotm.statemachine.model.domain.impl.TransitionImpl;
import io.github.zhdotm.statemachine.model.exception.StateMachineException;
import io.github.zhdotm.statemachine.model.exception.util.ExceptionUtil;
import io.github.zhdotm.statemachine.starter.web.annotation.StateMachineAction;
import io.github.zhdotm.statemachine.starter.web.annotation.StateMachineComponent;
import io.github.zhdotm.statemachine.starter.web.annotation.StateMachineCondition;
import lombok.SneakyThrows;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author zhihao.mao
 */

public interface ITransitionAdapter {

    default ITransition<String, String, String, String> getTransition() {
        Object obj = this;
        Class<?> clazz = this.getClass();
        String clazzName = clazz.getName();
        StateMachineComponent stateMachineComponent = clazz.getDeclaredAnnotation(StateMachineComponent.class);
        ExceptionUtil.isTrue(stateMachineComponent != null, StateMachineException.class, "获取转换失败: [%s]类上不存在@StateMachineComponent", clazzName);
        Method conditionMethod = getConditionMethod();
        String conditionMethodName = conditionMethod.getName();
        Class<?>[] conditionMethodParameterTypes = conditionMethod.getParameterTypes();
        Method actionMethod = getActionMethod();
        String actionMethodName = getActionMethod().getName();
        Class<?>[] actionMethodParameterTypes = actionMethod.getParameterTypes();
        ExceptionUtil.isTrue(matchMethodParameterTypes(conditionMethodParameterTypes, actionMethodParameterTypes), StateMachineException.class, "获取转换失败: [%s]类上的[%s]条件方法的参数和[%s]动作方法的参数不匹配", clazzName, conditionMethodName, actionMethodName);

        TransitionImpl<String, String, String, String> transition = TransitionImpl.getInstance();
        ConditionImpl<String, String, String> condition = ConditionImpl.getInstance();
        condition.conditionId(getConditionId())
                .check(new Function<IEventContext<String, String>, Boolean>() {
                    @SneakyThrows
                    @Override
                    public Boolean apply(IEventContext<String, String> eventContext) {
                        IEvent<String> event = eventContext.getEvent();
                        Object[] args = convertPayloadToArgs(event.getPayload(), conditionMethod, clazzName);

                        return (Boolean) conditionMethod.invoke(obj, args);
                    }
                });
        ActionImpl<String> action = ActionImpl.getInstance();
        action.actionId(getActionId())
                .execute(new Function<Object[], Object>() {
                    @SneakyThrows
                    @Override
                    public Object apply(Object[] args) {
                        args = convertPayloadToArgs(args, actionMethod, clazzName);

                        return actionMethod.invoke(obj, args);
                    }
                });
        transition.type(stateMachineComponent.type())
                .from(Arrays.asList(stateMachineComponent.from()))
                .on(stateMachineComponent.on())
                .when(condition)
                .perform(action);
        if (stateMachineComponent.type() == TransitionTypeEnum.EXTERNAL) {
            transition.to(stateMachineComponent.to());
        }

        return transition;
    }

    default Boolean matchMethodParameterTypes(Class<?>[] conditionMethodParameterTypes, Class<?>[] actionMethodParameterTypes) {
        if (conditionMethodParameterTypes.length != actionMethodParameterTypes.length) {

            return Boolean.FALSE;
        }

        for (int i = 0; i < conditionMethodParameterTypes.length; i++) {
            Class<?> conditionMethodParameterType = conditionMethodParameterTypes[i];
            Class<?> actionMethodParameterType = actionMethodParameterTypes[i];
            if (conditionMethodParameterType != actionMethodParameterType) {

                return Boolean.FALSE;
            }
        }

        return Boolean.TRUE;
    }

    default String getStateMachineId() {
        Class<?> clazz = this.getClass();
        String clazzName = clazz.getName();
        StateMachineComponent stateMachineComponent = clazz.getDeclaredAnnotation(StateMachineComponent.class);
        ExceptionUtil.isTrue(stateMachineComponent != null, StateMachineException.class, "获取stateMachineId失败: [%s]类上不存在@StateMachineComponent", clazzName);

        return stateMachineComponent.stateMachineId();
    }

    default String getConditionId() {
        Method conditionMethod = getConditionMethod();
        StateMachineCondition stateMachineCondition = conditionMethod.getDeclaredAnnotation(StateMachineCondition.class);

        return stateMachineCondition.conditionId();
    }

    default Method getConditionMethod() {
        Class<?> clazz = this.getClass();
        String clazzName = clazz.getName();
        Method method = getStateMachineComponentMethod(StateMachineCondition.class);
        String methodName = method.getName();
        Class<?> returnType = method.getReturnType();
        ExceptionUtil.isTrue(returnType == Boolean.class, StateMachineException.class, "[%s]类上的[%s]条件方法返回值不为Boolean类型", clazzName, methodName);

        return method;
    }

    default String getActionId() {
        Method actionMethod = getActionMethod();
        StateMachineAction stateMachineAction = actionMethod.getDeclaredAnnotation(StateMachineAction.class);

        return stateMachineAction.actionId();
    }

    default Method getActionMethod() {

        return getStateMachineComponentMethod(StateMachineAction.class);
    }

    default Method getStateMachineComponentMethod(Class<? extends Annotation> annotationClazz) {
        ExceptionUtil.isTrue(annotationClazz == StateMachineCondition.class || annotationClazz == StateMachineAction.class, StateMachineException.class, "TransitionAdapter.getStateMachineComponentMethod只支持@StateMachineCondition、@StateMachineAction");
        Class<?> clazz = this.getClass();
        String clazzName = clazz.getName();
        StateMachineComponent stateMachineComponent = clazz.getDeclaredAnnotation(StateMachineComponent.class);
        if (stateMachineComponent == null) {

            return null;
        }

        Method[] methods = clazz.getMethods();
        Method targetMethod = null;
        for (Method method : methods) {
            Annotation annotation = method.getDeclaredAnnotation(annotationClazz);
            if (annotation == null) {

                continue;
            }
            ExceptionUtil.isTrue(targetMethod == null, StateMachineException.class, "[%s]类的方法上存在多个[%s]注解", clazzName, annotationClazz.getName());
            targetMethod = method;
        }

        ExceptionUtil.isTrue(targetMethod != null, StateMachineException.class, "[%s]类的方法上不存在[%s]注解", clazzName, annotationClazz.getName());

        return targetMethod;
    }

    default Object[] convertPayloadToArgs(Object[] payload, Method method, String clazzName) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        String methodName = method.getName();
        payload = Optional
                .ofNullable(payload)
                .orElse(new Object[0]);
        int payloadLength = payload.length;
        int parameterTypesLength = parameterTypes.length;
        ExceptionUtil.isTrue(payloadLength <= parameterTypesLength, StateMachineException.class, "事件负载[%s]转换为[%s]类的[%s]方法的执行参数失败: 事件负载参数个数大于执行参数个数", Arrays.toString(payload), clazzName, methodName);
        Object[] args = new Object[parameterTypesLength];
        for (int i = 0; i < args.length; i++) {
            if (i < payloadLength) {
                ExceptionUtil.isTrue(payload[i].getClass() == parameterTypes[i], StateMachineException.class, "事件负载[%s]转换为[%s]类的[%s]方法的执行参数失败: 参数类型不匹配", Arrays.toString(payload), clazzName, methodName);
                args[i] = payload[i];
            }
        }

        return args;
    }

}
