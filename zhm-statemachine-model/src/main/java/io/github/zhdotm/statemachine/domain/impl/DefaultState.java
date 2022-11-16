package io.github.zhdotm.statemachine.domain.impl;

import io.github.zhdotm.statemachine.constant.StateTypeEnum;
import io.github.zhdotm.statemachine.domain.IState;

/**
 * 默认状态
 *
 * @author zhihao.mao
 */

public class DefaultState implements IState {

    private String stateId;

    private String stateMachineId;

    private StateTypeEnum type;

    private String nextStateMachineId;

    public DefaultState(String stateId) {
        this.stateId = stateId;
    }

    public DefaultState() {
    }

    public static DefaultState getInstance(String stateId) {

        return new DefaultState(stateId);
    }

    public static DefaultState getInstance() {

        return new DefaultState();
    }

    public DefaultState stateId(String stateId) {
        this.stateId = stateId;

        return this;
    }

    public DefaultState stateMachineId(String stateMachineId) {
        this.stateMachineId = stateMachineId;

        return this;
    }

    public DefaultState type(StateTypeEnum type) {
        this.type = type;

        return this;
    }

    public DefaultState nextStateMachineId(String nextStateMachineId) {
        this.nextStateMachineId = nextStateMachineId;

        return this;
    }

    @Override
    public String getStateMachineId() {

        return stateMachineId;
    }
    
    public void setStateMachineId(String stateMachineId) {
        this.stateMachineId = stateMachineId;
    }

    @Override
    public StateTypeEnum getType() {

        return this.type;
    }

    public void setType(StateTypeEnum type) {

        this.type = type;
    }

    @Override
    public String getNextStateMachineId() {

        return this.nextStateMachineId;
    }

    public void setNextStateMachineId(String nextStateMachineId) {

        this.nextStateMachineId = nextStateMachineId;
    }

    @Override
    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

}
