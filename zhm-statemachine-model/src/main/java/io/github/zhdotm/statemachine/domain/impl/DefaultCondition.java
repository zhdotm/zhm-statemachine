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

    public DefaultCondition(String conditionId, Function<IEvent, Boolean> isSatisfied) {
        this.conditionId = conditionId;
        this.isSatisfied = isSatisfied;
    }

    @Override
    public Boolean isSatisfied(IEvent event) {

        return isSatisfied.apply(event);
    }

}
