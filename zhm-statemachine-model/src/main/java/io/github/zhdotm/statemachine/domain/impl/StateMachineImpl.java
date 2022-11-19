package io.github.zhdotm.statemachine.domain.impl;

import io.github.zhdotm.statemachine.constant.TransitionTypeEnum;
import io.github.zhdotm.statemachine.domain.IState;
import io.github.zhdotm.statemachine.domain.IStateMachine;
import io.github.zhdotm.statemachine.domain.ITransition;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.SneakyThrows;

import java.util.*;

/**
 * @author zhihao.mao
 */

public class StateMachineImpl<M, S, E, C, A> implements IStateMachine<M, S, E, C, A> {

    private final Map<S, IState<S, E>> stateMap = new HashMap<>();
    private final Map<String, List<ITransition<S, E, C, A>>> externalTransitionMap = new HashMap<>();
    private final Map<String, List<ITransition<S, E, C, A>>> internalTransitionMap = new HashMap<>();
    @Getter
    @Setter
    private M stateMachineId;

    public static <M, S, E, C, A> StateMachineImpl<M, S, E, C, A> getInstance() {

        return new StateMachineImpl<>();
    }

    @Override
    public IStateMachine<M, S, E, C, A> stateMachineId(@NonNull M stateMachineId) {
        this.stateMachineId = stateMachineId;

        return this;
    }

    @Override
    public Collection<S> getStateIds() {

        return stateMap.keySet();
    }

    @Override
    public IState<S, E> getState(@NonNull S stateId) {

        return stateMap.get(stateId);
    }

    @Override
    public List<ITransition<S, E, C, A>> getExternalTransition(@NonNull S stateId, @NonNull E eventId) {

        return externalTransitionMap.get(stateId + "_" + eventId);
    }

    @Override
    public List<ITransition<S, E, C, A>> getInternalTransition(@NonNull S stateId, @NonNull E eventId) {

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
        for (S fromStateId : fromStateIds) {
            IState<S, E> state = getState(fromStateId);
            if (state == null) {
                state = StateImpl.getInstance();
            }
            state.addEventId(eventId);
            stateMap.put(fromStateId, state);

            Map<String, List<ITransition<S, E, C, A>>> transitionMap = null;
            if (type == TransitionTypeEnum.EXTERNAL) {

                transitionMap = externalTransitionMap;
            }
            if (type == TransitionTypeEnum.INTERNAL) {

                transitionMap = internalTransitionMap;
            }

            List<ITransition<S, E, C, A>> transitions = transitionMap.getOrDefault(fromStateId + "_" + eventId, new ArrayList<>());
            transitions.add(transition);
            internalTransitionMap.put(fromStateId + "_" + eventId, transitions);
        }
    }

}
