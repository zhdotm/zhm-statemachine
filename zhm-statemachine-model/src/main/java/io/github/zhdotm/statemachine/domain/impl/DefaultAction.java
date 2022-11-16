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

    public DefaultAction(String actionId, Function<Object[], Boolean> invoke) {
        this.actionId = actionId;
        this.invoke = invoke;
    }

    @Override
    public Boolean invoke(Object... args) {

        return invoke.apply(args);
    }

}
