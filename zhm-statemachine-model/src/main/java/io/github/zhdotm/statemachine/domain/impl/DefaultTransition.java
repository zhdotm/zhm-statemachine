package io.github.zhdotm.statemachine.domain.impl;

import io.github.zhdotm.statemachine.domain.abs.AbstractTransition;

/**
 * 默认转换
 *
 * @author zhihao.mao
 */

public class DefaultTransition extends AbstractTransition {

    private String transitionId;

    private String stateMachineId;

    public DefaultTransition(String transitionId) {
        this.transitionId = transitionId;
    }

    public DefaultTransition() {
    }

    public static DefaultTransition getInstance(String transitionId) {

        return new DefaultTransition(transitionId);
    }

    public static DefaultTransition getInstance() {

        return new DefaultTransition();
    }

    public DefaultTransition transitionId(String transitionId) {
        this.transitionId = transitionId;

        return this;
    }

    public DefaultTransition stateMachineId(String stateMachineId) {
        this.stateMachineId = stateMachineId;

        return this;
    }

    @Override
    public String getTransitionId() {
        return transitionId;
    }

    public void setTransitionId(String transitionId) {
        this.transitionId = transitionId;
    }

    @Override
    public String getStateMachineId() {

        return this.stateMachineId;
    }

    @Override
    public void setStateMachineId(String stateMachineId) {

        this.stateMachineId = stateMachineId;
    }
}
