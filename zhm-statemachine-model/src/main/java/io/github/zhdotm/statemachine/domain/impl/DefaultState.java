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

    @Override
    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

}
