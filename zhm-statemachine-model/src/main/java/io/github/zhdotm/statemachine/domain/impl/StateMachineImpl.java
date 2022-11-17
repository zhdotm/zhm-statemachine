package io.github.zhdotm.statemachine.domain.impl;

import io.github.zhdotm.statemachine.domain.*;
import io.github.zhdotm.statemachine.exception.StateMachineException;
import lombok.Getter;
import lombok.SneakyThrows;

import java.util.*;
import java.util.stream.Collectors;

public class StateMachineImpl<M, S, E, A> implements IStateMachine<M, S, E, A> {

    @Getter
    private M stateMachineId;

    private Map<S, IState<S, E>> stateMap = new HashMap<>();

    private Map<String, ITransition<S, E, A>> externalTransitionMap = new HashMap<>();

    private Map<String, List<ITransition<S, E, A>>> internalTransitionMap = new HashMap<>();

    @Override
    public Collection<S> getStateIds() {

        return stateMap.keySet();
    }

    @Override
    public IState<S, E> getState(S stateId) {

        return stateMap.get(stateId);
    }

    @SneakyThrows
    @Override
    public S fireEvent(IEventContext<S, E> eventContext) {
        S stateId = eventContext.getStateId();
        IEvent<E> event = eventContext.getEvent();

        IState<S, E> state = stateMap.get(stateId);
        if (state == null) {

            throw new StateMachineException("发布事件失败: 不存在对应的状态");
        }

        Collection<E> eventIds = state.getEventIds();
        E eventId = event.getEventId();
        if (!eventIds.contains(eventId)) {

            throw new StateMachineException("发布事件失败: 对应状态不存在指定事件");
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

    private ITransition<S, E, A> getExternalTransition(S stateId, E eventId) {

        return externalTransitionMap.get(stateId + "_" + eventId);
    }

    private List<ITransition<S, E, A>> getInternalTransition(S stateId, E eventId) {

        return internalTransitionMap.get(stateId + "_" + eventId);
    }

}
