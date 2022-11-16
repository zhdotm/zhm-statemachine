package io.github.zhdotm.statemachine.domain.impl;


import io.github.zhdotm.statemachine.domain.IEvent;
import io.github.zhdotm.statemachine.domain.IEventContext;

/**
 * 事件上下文
 *
 * @author zhihao.mao
 */

public class DefaultEventContext implements IEventContext {

    private String stateMachineId;

    private String stateId;

    private IEvent event;

    public DefaultEventContext(String stateMachineId, String stateId, IEvent event) {
        this.stateMachineId = stateMachineId;
        this.stateId = stateId;
        this.event = event;
    }

    public DefaultEventContext() {
    }

    public static DefaultEventContext getInstance(String stateMachineId, String stateId, IEvent event) {

        return new DefaultEventContext(stateMachineId, stateId, event);
    }

    public static DefaultEventContext getInstance() {

        return new DefaultEventContext();
    }

    public DefaultEventContext stateMachineId(String stateMachineId) {
        this.stateMachineId = stateMachineId;

        return this;
    }

    @Override
    public String getStateMachineId() {
        return stateMachineId;
    }

    @Override
    public String getStateId() {
        return stateId;
    }

    @Override
    public IEvent getEvent() {
        return event;
    }

    public void setStateMachineId(String stateMachineId) {
        this.stateMachineId = stateMachineId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public void setEvent(IEvent event) {
        this.event = event;
    }

}
