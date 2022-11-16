package io.github.zhdotm.statemachine.domain.impl;


import io.github.zhdotm.statemachine.domain.IAction;

import java.util.function.Function;

/**
 * 默认动作
 *
 * @author zhihao.mao
 */

public class DefaultAction implements IAction {

    private String actionId;

    private Function<Object[], Boolean> invoke;

    private String stateMachineId;

    public DefaultAction(String actionId, Function<Object[], Boolean> invoke) {
        this.actionId = actionId;
        this.invoke = invoke;
    }

    public DefaultAction() {
    }

    public static DefaultAction getInstance(String actionId, Function<Object[], Boolean> invoke) {

        return new DefaultAction(actionId, invoke);
    }

    public static DefaultAction getInstance() {

        return new DefaultAction();
    }

    public DefaultAction actionId(String actionId) {
        this.actionId = actionId;

        return this;
    }

    public DefaultAction invoke(Function<Object[], Boolean> invoke) {
        this.invoke = invoke;

        return this;
    }

    public DefaultAction stateMachineId(String stateMachineId) {
        this.stateMachineId = stateMachineId;

        return this;
    }

    @Override
    public String getActionId() {
        return actionId;
    }

    public Function<Object[], Boolean> getInvoke() {
        return invoke;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public void setInvoke(Function<Object[], Boolean> invoke) {
        this.invoke = invoke;
    }

    @Override
    public String getStateMachineId() {

        return stateMachineId;
    }

    public void setStateMachineId(String stateMachineId) {

        this.stateMachineId = stateMachineId;
    }

    @Override
    public Boolean invoke(Object... args) {

        return invoke.apply(args);
    }

}
