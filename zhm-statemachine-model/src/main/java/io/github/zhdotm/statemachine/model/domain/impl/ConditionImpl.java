package io.github.zhdotm.statemachine.model.domain.impl;

import io.github.zhdotm.statemachine.model.domain.ICondition;
import io.github.zhdotm.statemachine.model.domain.IEventContext;
import lombok.Getter;
import lombok.NonNull;

import java.util.function.Function;

/**
 * @author zhihao.mao
 */

public class ConditionImpl<S, E, C> implements ICondition<S, E, C> {

    private Function<IEventContext<S, E>, Boolean> check;

    @Getter
    private C conditionId;

    public static <S, E, C> ConditionImpl<S, E, C> getInstance() {

        return new ConditionImpl<>();
    }

    public ConditionImpl<S, E, C> check(@NonNull Function<IEventContext<S, E>, Boolean> check) {
        this.check = check;

        return this;
    }

    public ConditionImpl<S, E, C> conditionId(@NonNull C conditionId) {
        this.conditionId = conditionId;

        return this;
    }

    @Override
    public Boolean isSatisfied(@NonNull IEventContext<S, E> eventContext) {

        return check.apply(eventContext);
    }

}
