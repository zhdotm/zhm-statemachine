package io.github.zhdotm.statemachine.domain;


import io.github.zhdotm.statemachine.exception.StateMachineException;
import lombok.SneakyThrows;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhihao.mao
 */

public interface IStateMachine<M, S, E, A> {

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
    IStateMachine<M, S, E, A> stateMachineId(M stateMachineId);

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
    ITransition<S, E, A> getExternalTransition(S stateId, E eventId);

    /**
     * 获取内部转换
     *
     * @param stateId 状态ID
     * @param eventId 事件ID
     * @return 转换
     */
    List<ITransition<S, E, A>> getInternalTransition(S stateId, E eventId);

    /**
     * 添加转换
     *
     * @param transitions 添加转换
     */
    void addTransitions(List<ITransition<S, E, A>> transitions);

    /**
     * 发布事件
     *
     * @param eventContext 事件上下文
     * @return 转换成功后的状态ID
     */
    @SneakyThrows
    default S fireEvent(IEventContext<S, E> eventContext) {
        S stateId = eventContext.getStateId();
        IEvent<E> event = eventContext.getEvent();
        E eventId = event.getEventId();

        IState<S, E> state = getState(stateId);
        if (state == null) {

            throw new StateMachineException(String.format("发布事件[%s]失败: 不存在对应的状态[%s]", eventId, stateId));
        }

        Collection<E> eventIds = state.getEventIds();
        if (!eventIds.contains(eventId)) {

            throw new StateMachineException(String.format("发布事件[%s]失败: 对应状态[%s]不存在指定事件[%S]", eventId, stateId, eventId));
        }

        List<ITransition<S, E, A>> internalTransitions = Optional.ofNullable(getInternalTransition(stateId, eventId))
                .orElse(new ArrayList<>())
                .stream()
                .sorted(Comparator.comparingInt(ITransition::getSort))
                .collect(Collectors.toList());
        for (ITransition<S, E, A> internalTransition : internalTransitions) {
            internalTransition.transfer(eventContext);
        }

        ITransition<S, E, A> externalTransition = getExternalTransition(stateId, eventId);
        if (externalTransition == null) {

            return stateId;
        }

        return externalTransition.transfer(eventContext);
    }
}
