package io.github.zhdotm.statemachine.model.domain;


import io.github.zhdotm.statemachine.model.domain.impl.EventContextImpl;
import io.github.zhdotm.statemachine.model.domain.impl.EventImpl;
import io.github.zhdotm.statemachine.model.exception.StateMachineException;
import io.github.zhdotm.statemachine.model.exception.util.ExceptionUtil;
import io.github.zhdotm.statemachine.model.log.StateMachineLog;
import lombok.SneakyThrows;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhihao.mao
 */

public interface IStateMachine<M, S, E, C, A> {

    ThreadLocal<String> STATEMACHINE_ID_THREAD_LOCAL = new ThreadLocal<>();
    ThreadLocal<String> TRACE_ID_THREAD_LOCAL = new ThreadLocal<>();
    ThreadLocal<String> CURRENT_STATE_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 获取状态机ID
     *
     * @return 状态机ID
     */
    M getStateMachineId();

    /**
     * 设置状态机ID
     *
     * @param stateMachineId 状态机ID
     * @return 状态机
     */
    IStateMachine<M, S, E, C, A> stateMachineId(M stateMachineId);

    /**
     * 获取所有状态ID
     *
     * @return 状态ID
     */
    Collection<S> getStateIds();

    /**
     * 根据状态ID获取状态
     *
     * @param stateId 状态ID
     * @return 状态
     */
    IState<S, E> getState(S stateId);

    /**
     * 获取外部转换
     *
     * @param stateId 状态ID
     * @param eventId 事件ID
     * @return 转换
     */
    List<ITransition<S, E, C, A>> getExternalTransition(S stateId, E eventId);

    /**
     * 获取内部转换
     *
     * @param stateId 状态ID
     * @param eventId 事件ID
     * @return 转换
     */
    List<ITransition<S, E, C, A>> getInternalTransition(S stateId, E eventId);

    /**
     * 添加转换
     *
     * @param transitions 添加转换
     */
    void addTransitions(List<ITransition<S, E, C, A>> transitions);

    /**
     * 发布事件
     *
     * @param stateId 状态ID
     * @param eventId 事件ID
     * @param payload 事件负载
     * @return 状态上下文
     */
    default IStateContext<S, E> fireEvent(S stateId, E eventId, Object... payload) {
        EventContextImpl<S, E> eventContext = EventContextImpl.getInstance();
        EventImpl<E> event = EventImpl.getInstance();
        event.eventId(eventId)
                .payload(payload);
        eventContext.from(stateId)
                .on(event);

        return fireEvent(eventContext);
    }

    /**
     * 发布事件
     *
     * @param eventContext 事件上下文
     * @return 转换成功后的状态ID
     */
    @SneakyThrows
    default IStateContext<S, E> fireEvent(IEventContext<S, E> eventContext) {
        STATEMACHINE_ID_THREAD_LOCAL.set(String.valueOf(getStateMachineId()));
        TRACE_ID_THREAD_LOCAL.set(String.valueOf(System.currentTimeMillis()));
        CURRENT_STATE_THREAD_LOCAL.set(String.valueOf(eventContext.getStateId()));
        IStateContext<S, E> stateContext = null;
        try {
            S stateId = eventContext.getStateId();
            IEvent<E> event = eventContext.getEvent();
            E eventId = event.getEventId();
            Object[] payload = event.getPayload();

            StateMachineLog.info("状态机流程日志[%s, %s]: 当前状态[%s]收到携带负载[%s]的事件[%s]", STATEMACHINE_ID_THREAD_LOCAL.get(), TRACE_ID_THREAD_LOCAL.get(), stateId, Arrays.toString(payload), eventId);

            IState<S, E> state = getState(stateId);
            ExceptionUtil.isTrue(state != null, StateMachineException.class, "状态机[%s, %s]发布事件[%s]失败: 不存在对应的状态[%s]", STATEMACHINE_ID_THREAD_LOCAL.get(), TRACE_ID_THREAD_LOCAL.get(), eventId, stateId);

            Collection<E> eventIds = state.getEventIds();
            ExceptionUtil.isTrue(eventIds.contains(eventId), StateMachineException.class, "状态机[%s, %s]发布事件[%s]失败: 对应状态[%s]不存在指定事件[%s]", STATEMACHINE_ID_THREAD_LOCAL.get(), TRACE_ID_THREAD_LOCAL.get(), eventId, stateId, eventId);

            List<ITransition<S, E, C, A>> satisfiedInternalTransitions = Optional.ofNullable(getInternalTransition(stateId, eventId))
                    .orElse(new ArrayList<>())
                    .stream()
                    .filter(transition -> transition.getCondition().isSatisfied(eventContext))
                    .sorted(Comparator.comparingInt(ITransition::getSort))
                    .collect(Collectors.toList());

            List<ITransition<S, E, C, A>> satisfiedExternalTransitions = Optional.ofNullable(getExternalTransition(stateId, eventId))
                    .orElse(new ArrayList<>())
                    .stream()
                    .filter(transition -> transition.getCondition().isSatisfied(eventContext))
                    .collect(Collectors.toList());

            ExceptionUtil.isTrue(satisfiedExternalTransitions.size() <= 1, StateMachineException.class, "状态机[%s]发布事件[%s]失败: 状态[%s]指定事件[%S]对应的符合条件的外部转换超过一个", STATEMACHINE_ID_THREAD_LOCAL.get(), eventId, stateId, eventId);

            for (ITransition<S, E, C, A> internalTransition : satisfiedInternalTransitions) {
                stateContext = internalTransition.transfer(eventContext);
            }

            for (ITransition<S, E, C, A> satisfiedExternalTransition : satisfiedExternalTransitions) {

                stateContext = satisfiedExternalTransition.transfer(eventContext);
            }
        } finally {
            STATEMACHINE_ID_THREAD_LOCAL.remove();
            TRACE_ID_THREAD_LOCAL.remove();
            CURRENT_STATE_THREAD_LOCAL.remove();
        }

        return stateContext;
    }

    /**
     * 打印状态机内部结构
     */
    default void print() {
        StateMachineLog.info(StateMachineLog.tail("状态机结构日志: ---状态机[" + getStateMachineId() + "]", 100, "开始"));
        getStateIds()
                .forEach(stationId -> {
                    StateMachineLog.info(StateMachineLog.tail("状态机结构日志: ------状态[" + stationId + "]", 90, "开始"));
                    IState<S, E> state = getState(stationId);
                    state.getEventIds()
                            .forEach(eventId -> {
                                StateMachineLog.info(StateMachineLog.tail("状态机结构日志: ---------事件[" + eventId + "]", 80, "开始"));
                                List<ITransition<S, E, C, A>> internalTransitions = getInternalTransition(stationId, eventId);
                                if (internalTransitions != null && internalTransitions.size() > 0) {
                                    StateMachineLog.info(StateMachineLog.tail("状态机结构日志: ------------内部转换", 70, "开始"));
                                    for (ITransition<S, E, C, A> internalTransition : internalTransitions) {
                                        C conditionId = internalTransition.getCondition().getConditionId();
                                        StateMachineLog.info("状态机结构日志: ---------------判断条件[%s]", conditionId);
                                        A actionId = internalTransition.getAction().getActionId();
                                        StateMachineLog.info("状态机结构日志: ------------------执行动作[%s]", actionId);
                                    }
                                    StateMachineLog.info(StateMachineLog.tail("状态机结构日志: ------------内部转换", 70, "结束"));
                                }
                                List<ITransition<S, E, C, A>> externalTransitions = getExternalTransition(stationId, eventId);
                                if (externalTransitions != null && externalTransitions.size() > 0) {
                                    StateMachineLog.info(StateMachineLog.tail("状态机结构日志: ------------外部转换", 70, "开始"));
                                    for (ITransition<S, E, C, A> externalTransition : externalTransitions) {
                                        C conditionId = externalTransition.getCondition().getConditionId();
                                        StateMachineLog.info("状态机结构日志: ---------------判断条件[%s]", conditionId);
                                        A actionId = externalTransition.getAction().getActionId();
                                        StateMachineLog.info("状态机结构日志: ------------------执行动作[%s]", actionId);
                                        S toStateId = externalTransition.getToStateId();
                                        StateMachineLog.info("状态机结构日志: ------------------转换状态[%s]", toStateId);
                                    }
                                    StateMachineLog.info(StateMachineLog.tail("状态机结构日志: ------------外部转换", 70, "结束"));
                                }
                                StateMachineLog.info(StateMachineLog.tail("状态机结构日志: ---------事件[" + eventId + "]", 80, "结束"));
                            });
                    StateMachineLog.info(StateMachineLog.tail("状态机结构日志: ------状态[" + stationId + "]", 90, "结束"));
                });
        StateMachineLog.info(StateMachineLog.tail("状态机结构日志: ---状态机[" + getStateMachineId() + "]", 100, "结束"));
    }

}
