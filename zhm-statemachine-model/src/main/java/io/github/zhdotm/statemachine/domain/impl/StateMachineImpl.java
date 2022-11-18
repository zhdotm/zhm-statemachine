package io.github.zhdotm.statemachine.domain.impl;

import io.github.zhdotm.statemachine.constant.TransitionTypeEnum;
import io.github.zhdotm.statemachine.domain.IState;
import io.github.zhdotm.statemachine.domain.IStateMachine;
import io.github.zhdotm.statemachine.domain.ITransition;
import io.github.zhdotm.statemachine.exception.StateMachineException;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.util.*;

/**
 * @author zhihao.mao
 */

public class StateMachineImpl<M, S, E, C, A> implements IStateMachine<M, S, E, C, A> {

    private final Map<S, IState<S, E>> stateMap = new HashMap<>();
    private final Map<String, ITransition<S, E, C, A>> externalTransitionMap = new HashMap<>();
    private final Map<String, List<ITransition<S, E, C, A>>> internalTransitionMap = new HashMap<>();
    @Getter
    @Setter
    private M stateMachineId;

    public static <M, S, E, C, A> StateMachineImpl<M, S, E, C, A> getInstance() {

        return new StateMachineImpl<>();
    }

    @Override
    public IStateMachine<M, S, E, C, A> stateMachineId(M stateMachineId) {
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
    public ITransition<S, E, C, A> getExternalTransition(S stateId, E eventId) {

        return externalTransitionMap.get(stateId + "_" + eventId);
    }

    @Override
    public List<ITransition<S, E, C, A>> getInternalTransition(S stateId, E eventId) {

        return internalTransitionMap.get(stateId + "_" + eventId);
    }

    @Override
    public void addTransitions(List<ITransition<S, E, C, A>> transitions) {
        for (ITransition<S, E, C, A> transition : transitions) {

            addTransition(transition);
        }
    }

    @SneakyThrows
    private void addTransition(ITransition<S, E, C, A> transition) {
        TransitionTypeEnum type = transition.getType();
        Collection<S> fromStateIds = transition.getFromStateIds();
        E eventId = transition.getEventId();
        if (type == TransitionTypeEnum.EXTERNAL) {
            for (S fromStateId : fromStateIds) {
                ITransition<S, E, C, A> externalTransition = getExternalTransition(fromStateId, eventId);
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
                List<ITransition<S, E, C, A>> transitions = internalTransitionMap.getOrDefault(fromStateId + "_" + eventId, new ArrayList<>());
                transitions.add(transition);
                internalTransitionMap.put(fromStateId + "_" + eventId, transitions);
            }
        }
    }

}
