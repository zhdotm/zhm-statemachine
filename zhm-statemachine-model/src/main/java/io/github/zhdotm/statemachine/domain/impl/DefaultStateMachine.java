package io.github.zhdotm.statemachine.domain.impl;


import io.github.zhdotm.statemachine.domain.IState;
import io.github.zhdotm.statemachine.domain.IStateMachine;
import io.github.zhdotm.statemachine.domain.ITransition;
import io.github.zhdotm.statemachine.exception.BizStateMachineException;
import io.github.zhdotm.statemachine.support.IStateMachineSupport;

import java.util.Collection;
import java.util.List;

/**
 * 默认状态机
 *
 * @author zhihao.mao
 */

public class DefaultStateMachine implements IStateMachine {

    private final IStateMachine stateMachine;

    public DefaultStateMachine(String stateMachineId, List<ITransition> allTransitionList, IStateMachineSupport stateMachineSupport) throws BizStateMachineException {
        stateMachine = stateMachineSupport.build(stateMachineId, allTransitionList);
    }

    @Override
    public String getStateMachineId() {

        return stateMachine.getStateMachineId();
    }

    @Override
    public Collection<IState> getStates() {

        return stateMachine.getStates();
    }

    @Override
    public IState getStateById(String stateId) {

        return stateMachine.getStateById(stateId);
    }

    @Override
    public Collection<ITransition> getTransition(IState state) {

        return stateMachine.getTransition(state);
    }

}
