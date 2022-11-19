package io.github.zhdotm.statemachine.model.domain;


import io.github.zhdotm.statemachine.model.exception.StateMachineException;
import io.github.zhdotm.statemachine.model.exception.util.ExceptionUtil;
import io.github.zhdotm.statemachine.model.log.ProcessLog;
import lombok.SneakyThrows;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhihao.mao
 */

public interface IStateMachine<M, S, E, C, A> {

    ThreadLocal<String> STATEMACHINE_ID_THREAD_LOCAL = new ThreadLocal<>();

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
     * @param eventContext 事件上下文
     * @return 转换成功后的状态ID
     */
    @SneakyThrows
    default IStateContext<S, E> fireEvent(IEventContext<S, E> eventContext) {
        STATEMACHINE_ID_THREAD_LOCAL.set(getStateMachineId().toString());
        IStateContext<S, E> stateContext = null;
        try {
            S stateId = eventContext.getStateId();
            IEvent<E> event = eventContext.getEvent();
            E eventId = event.getEventId();
            Object[] payload = event.getPayload();

            ProcessLog.info("流程日志[%s]: 状态[%s]收到触发事件[%s]携带负载[%s]", STATEMACHINE_ID_THREAD_LOCAL.get(), stateId, eventId, Arrays.toString(payload));

            IState<S, E> state = getState(stateId);
            ExceptionUtil.isTrue(state != null, StateMachineException.class, "发布事件[%s]失败: 不存在对应的状态[%s]", eventId, stateId);

            Collection<E> eventIds = state.getEventIds();
            ExceptionUtil.isTrue(eventIds.contains(eventId), StateMachineException.class, "发布事件[%s]失败: 对应状态[%s]不存在指定事件[%S]", eventId, stateId, eventId);

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

            ExceptionUtil.isTrue(satisfiedExternalTransitions.size() <= 1, StateMachineException.class, "发布事件[%s]失败: 状态[%s]指定事件[%S]对应的符合条件的外部转换超过一个", eventId, stateId, eventId);

            for (ITransition<S, E, C, A> internalTransition : satisfiedInternalTransitions) {
                stateContext = internalTransition.transfer(eventContext);
            }

            for (ITransition<S, E, C, A> satisfiedExternalTransition : satisfiedExternalTransitions) {

                stateContext = satisfiedExternalTransition.transfer(eventContext);
            }
        } finally {
            STATEMACHINE_ID_THREAD_LOCAL.remove();
        }

        return stateContext;
    }

}
