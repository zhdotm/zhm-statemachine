package io.github.zhdotm.statemachine.domain.impl;


import io.github.zhdotm.statemachine.domain.ICondition;
import io.github.zhdotm.statemachine.domain.IEvent;

import java.util.function.Function;

/**
 * 默认条件
 *
 * @author zhihao.mao
 */

public class DefaultCondition implements ICondition {

    private String conditionId;

    private Function<IEvent, Boolean> isSatisfied;

    public DefaultCondition(String conditionId, Function<IEvent, Boolean> isSatisfied) {
        this.conditionId = conditionId;
        this.isSatisfied = isSatisfied;
    }

    public DefaultCondition() {
    }

    public static DefaultCondition getInstance(String conditionId, Function<IEvent, Boolean> isSatisfied) {

        return new DefaultCondition(conditionId, isSatisfied);
    }

    public static DefaultCondition getInstance() {

        return new DefaultCondition();
    }

    public DefaultCondition conditionId(String conditionId) {
        this.conditionId = conditionId;

        return this;
    }

    public DefaultCondition isSatisfied(Function<IEvent, Boolean> isSatisfied) {
        this.isSatisfied = isSatisfied;

        return this;
    }

    @Override
    public String getConditionId() {
        return conditionId;
    }

    public Function<IEvent, Boolean> getIsSatisfied() {
        return isSatisfied;
    }

    public void setConditionId(String conditionId) {
        this.conditionId = conditionId;
    }

    public void setIsSatisfied(Function<IEvent, Boolean> isSatisfied) {
        this.isSatisfied = isSatisfied;
    }

    @Override
    public Boolean isSatisfied(IEvent event) {

        return isSatisfied.apply(event);
    }

}
