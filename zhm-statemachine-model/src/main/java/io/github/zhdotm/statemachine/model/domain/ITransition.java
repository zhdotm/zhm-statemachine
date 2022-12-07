package io.github.zhdotm.statemachine.model.domain;


import io.github.zhdotm.statemachine.model.constant.TransitionTypeEnum;
import io.github.zhdotm.statemachine.model.log.ProcessLog;
import io.github.zhdotm.statemachine.model.support.StateContextFactory;
import io.github.zhdotm.statemachine.model.support.builder.context.state.IStateContextBuilder;
import io.github.zhdotm.statemachine.model.support.builder.context.state.IStateContextOnBuilder;
import io.github.zhdotm.statemachine.model.support.builder.context.state.IStateContextToBuilder;
import lombok.SneakyThrows;

import java.util.Collection;
import java.util.List;

/**
 * @author zhihao.mao
 */

public interface ITransition<S, E, C, A> {

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
    ITransition<S, E, C, A> type(TransitionTypeEnum type);

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
    ITransition<S, E, C, A> sort(Integer sort);

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
    ITransition<S, E, C, A> from(List<S> stateIds);

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
    ITransition<S, E, C, A> on(E eventId);

    /**
     * 获取转换条件
     *
     * @return 转换条件
     */
    ICondition<S, E, C> getCondition();

    /**
     * 设置条件
     *
     * @param condition 条件
     * @return 转换
     */
    ITransition<S, E, C, A> when(ICondition<S, E, C> condition);

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
    ITransition<S, E, C, A> perform(IAction<A> action);

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
    ITransition<S, E, C, A> to(S stateId);

    @SneakyThrows
    default IStateContext<S, E> transfer(IEventContext<S, E> eventContext) {
        S stateId = eventContext.getStateId();
        IEvent<E> event = eventContext.getEvent();
        E eventId = event.getEventId();
        Object[] payload = event.getPayload();
        IAction<A> action = getAction();
        A actionId = action.getActionId();

        ProcessLog.info("状态机流程日志[%s, %s]: 成功匹配[%s]动作[%s]", IStateMachine.STATEMACHINE_ID_THREAD_LOCAL.get(), IStateMachine.TRACE_ID_THREAD_LOCAL.get(), getType().getDescription(), actionId);
        Object result = action.invoke(eventContext.getEvent().getPayload());
        S toStateId = getToStateId();

        IStateContextBuilder<S, E> stateContextBuilder = StateContextFactory.create();
        IStateContextOnBuilder<S, E> stateContextOnBuilder = stateContextBuilder.on(eventContext);

        IStateContextToBuilder<S, E> stateContextToBuilder = null;
        if (getType() == TransitionTypeEnum.EXTERNAL) {

            stateContextToBuilder = stateContextOnBuilder.to(toStateId);
        }

        if (getType() == TransitionTypeEnum.INTERNAL) {

            stateContextToBuilder = stateContextOnBuilder.to(stateId);
        }
        IStateContext<S, E> stateContext = stateContextToBuilder.ret(result);

        ProcessLog.info("状态机流程日志[%s, %s]: 执行结果[%s], 执行后状态[%s]", IStateMachine.STATEMACHINE_ID_THREAD_LOCAL.get(), IStateMachine.TRACE_ID_THREAD_LOCAL.get(), stateContext.getPayload(), stateContext.getStateId());
        return stateContext;
    }
}
