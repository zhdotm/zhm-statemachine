package io.github.zhdotm.statemachine.domain;


import io.github.zhdotm.statemachine.exception.StateMachineException;
import lombok.SneakyThrows;

import java.util.Collection;

/**
 * 转换
 *
 * @author zhihao.mao
 */

public interface ITransition<S, E, A> {

    /**
     * 获取排序号
     *
     * @return 排序号
     */
    default Integer getSort() {

        return Integer.MAX_VALUE;
    }

    /**
     * 获取转换来源状态ID
     *
     * @return 转换来源状态ID
     */
    Collection<S> getFromStateIds();

    /**
     * 获取转换条件
     *
     * @return 转换条件
     */
    ICondition<S, E> getCondition();

    /**
     * 获取动作
     *
     * @return 动作
     */
    IAction<A> getAction();

    /**
     * 获取转换成功后的状态ID
     *
     * @return 状态ID
     */
    S getToStateId();

    @SneakyThrows
    default S transfer(IEventContext<S, E> eventContext) {
        ICondition<S, E> condition = getCondition();
        if (!condition.isSatisfied(eventContext)) {

            throw new StateMachineException("执行转换失败: 条件未通过");
        }

        IAction<A> action = getAction();
        if (!action.invoke(eventContext.getEvent().getPayload())) {

            throw new StateMachineException("执行转换失败: 执行动作失败");
        }

        return getToStateId();
    }
}
