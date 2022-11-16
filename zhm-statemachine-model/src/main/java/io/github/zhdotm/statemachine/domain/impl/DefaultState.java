package io.github.zhdotm.statemachine.domain.impl;

import io.github.zhdotm.statemachine.domain.IState;

/**
 * 默认状态
 *
 * @author zhihao.mao
 */

public class DefaultState implements IState {

    private String stateId;

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

    @Override
    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

}
