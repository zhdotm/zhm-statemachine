package io.github.zhdotm.statemachine.domain.impl;

import io.github.zhdotm.statemachine.constant.TransitionTypeEnum;
import io.github.zhdotm.statemachine.domain.*;
import io.github.zhdotm.statemachine.exception.StateMachineException;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhihao.mao
 */

public class StateMachineImpl<M, S, E, A> implements IStateMachine<M, S, E, A> {

    @Getter
    @Setter
    private M stateMachineId;

    private final Map<S, IState<S, E>> stateMap = new HashMap<>();

    private final Map<String, ITransition<S, E, A>> externalTransitionMap = new HashMap<>();

    private final Map<String, List<ITransition<S, E, A>>> internalTransitionMap = new HashMap<>();

    public static <M, S, E, A> StateMachineImpl<M, S, E, A> getInstance() {

        return new StateMachineImpl<>();
    }

    @Override
    public IStateMachine<M, S, E, A> stateMachineId(M stateMachineId) {
        this.stateMachineId = stateMachineId;

        return this;
    }

    @Override
    public Collection<S> getStateIds() {

        return stateMap.keySet();
    }

    @Override
    public IState<S, E> getState(S stateId) {

        return stateMap.get(stateId);
    }

    @Override
    public ITransition<S, E, A> getExternalTransition(S stateId, E eventId) {

        return externalTransitionMap.get(stateId + "_" + eventId);
    }

    @Override
    public List<ITransition<S, E, A>> getInternalTransition(S stateId, E eventId) {

        return internalTransitionMap.get(stateId + "_" + eventId);
    }

    @Override
    public void addTransitions(List<ITransition<S, E, A>> transitions) {
        for (ITransition<S, E, A> transition : transitions) {

            addTransition(transition);
        }
    }

    @SneakyThrows
    private void addTransition(ITransition<S, E, A> transition) {
        TransitionTypeEnum type = transition.getType();
        Collection<S> fromStateIds = transition.getFromStateIds();
        E eventId = transition.getEventId();
        if (type == TransitionTypeEnum.EXTERNAL) {
            for (S fromStateId : fromStateIds) {
                ITransition<S, E, A> externalTransition = getExternalTransition(fromStateId, eventId);
                if (externalTransition != null) {

                    throw new StateMachineException(String.format("添加转换失败, 一个状态[%s]和事件[%s]确定的唯一外部转换已经存在", fromStateId, eventId));
                }
            }
        }

        for (S fromStateId : fromStateIds) {
            IState<S, E> state = getState(fromStateId);
            if (state == null) {
                state = StateImpl.getInstance();
            }
            state.addEventId(eventId);
            stateMap.put(fromStateId, state);
            if (type == TransitionTypeEnum.EXTERNAL) {
                externalTransitionMap.put(fromStateId + "_" + eventId, transition);
            }
            if (type == TransitionTypeEnum.INTERNAL) {
                List<ITransition<S, E, A>> transitions = internalTransitionMap.getOrDefault(fromStateId + "_" + eventId, new ArrayList<>());
                transitions.add(transition);
                internalTransitionMap.put(fromStateId + "_" + eventId, transitions);
            }
        }
    }

}
