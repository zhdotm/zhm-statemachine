package io.github.zhdotm.statemachine.domain.abs;


import io.github.zhdotm.statemachine.constant.TransitionTypeEnum;
import io.github.zhdotm.statemachine.domain.IAction;
import io.github.zhdotm.statemachine.domain.ICondition;
import io.github.zhdotm.statemachine.domain.IState;
import io.github.zhdotm.statemachine.domain.ITransition;

/**
 * 转换抽象类
 *
 * @author zhihao.mao
 */

public abstract class AbstractTransition implements ITransition {

    private TransitionTypeEnum type;

    private IState stateFrom;

    private IState stateTo;

    private ICondition condition;

    private IAction action;

    private Integer sortId;

    @Override
    public ITransition type(TransitionTypeEnum transitionTypeEnum) {
        this.type = transitionTypeEnum;

        return this;
    }

    @Override
    public ITransition from(IState state) {
        this.stateFrom = state;

        return this;
    }

    @Override
    public ITransition to(IState state) {
        this.stateTo = state;

        return this;
    }

    @Override
    public ITransition condition(ICondition condition) {
        this.condition = condition;

        return this;
    }

    @Override
    public ITransition action(IAction action) {
        this.action = action;

        return this;
    }

    @Override
    public ITransition sortId(Integer sortId) {
        this.sortId = sortId;

        return this;
    }

    @Override
    public TransitionTypeEnum getType() {

        return type;
    }

    public IState getStateFrom() {

        return stateFrom;
    }

    public IState getStateTo() {

        return stateTo;
    }

    @Override
    public ICondition getCondition() {

        return condition;
    }

    @Override
    public IAction getAction() {

        return action;
    }

    @Override
    public Integer getSortId() {

        return sortId;
    }

    @Override
    public IState getCurrentState() {

        return this.stateFrom;
    }

    @Override
    public IState getNextState() {

        return this.stateTo;
    }

}
