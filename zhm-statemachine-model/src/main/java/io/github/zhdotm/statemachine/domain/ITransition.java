package io.github.zhdotm.statemachine.domain;


import io.github.zhdotm.statemachine.constant.TransitionTypeEnum;
import io.github.zhdotm.statemachine.exception.StateMachineException;
import lombok.SneakyThrows;

import java.util.Collection;
import java.util.List;

/**
 * @author zhihao.mao
 */

public interface ITransition<S, E, A> {

    /**
     * 获取转换类型
     *
     * @return 转换类型
     */
    TransitionTypeEnum getType();

    /**
     * 设置转换类型
     *
     * @param type 类型
     * @return 转换
     */
    ITransition<S, E, A> type(TransitionTypeEnum type);

    /**
     * 获取排序号
     *
     * @return 排序号
     */
    Integer getSort();

    /**
     * 设置排序号
     *
     * @param sort 排序号
     * @return 转换
     */
    ITransition<S, E, A> sort(Integer sort);

    /**
     * 获取转换来源状态ID
     *
     * @return 转换来源状态ID
     */
    Collection<S> getFromStateIds();

    /**
     * 设置初始状态
     *
     * @param stateIds 初始状态
     * @return 转换
     */
    ITransition<S, E, A> from(List<S> stateIds);

    /**
     * 获取事件ID
     *
     * @return
     */
    E getEventId();

    /**
     * 设置事件ID
     *
     * @param eventId 事件ID
     * @return 转换
     */
    ITransition<S, E, A> on(E eventId);

    /**
     * 获取转换条件
     *
     * @return 转换条件
     */
    ICondition<S, E> getCondition();

    /**
     * 设置条件
     *
     * @param condition 条件
     * @return 转换
     */
    ITransition<S, E, A> when(ICondition<S, E> condition);

    /**
     * 获取动作
     *
     * @return 动作
     */
    IAction<A> getAction();

    /**
     * 设置动作
     *
     * @param action 动作
     * @return 转换
     */
    ITransition<S, E, A> perform(IAction<A> action);

    /**
     * 获取转换成功后的状态ID
     *
     * @return 状态ID
     */
    S getToStateId();

    /**
     * 设置转换成功后的状态ID
     *
     * @param stateId 状态ID
     * @return 转换
     */
    ITransition<S, E, A> to(S stateId);

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
