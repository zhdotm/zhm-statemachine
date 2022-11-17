package io.github.zhdotm.statemachine.domain.impl;

import io.github.zhdotm.statemachine.domain.IAction;
import lombok.Getter;

import java.util.function.Function;

public class ActionImpl<A> implements IAction<A> {

    @Getter
    private A actionId;

    private Function<Object[], Boolean> doAction;

    public static <A> ActionImpl<A> getInstance() {

        return new ActionImpl<>();
    }

    public ActionImpl<A> actionId(A actionId) {
        this.actionId = actionId;

        return this;
    }

    public ActionImpl<A> doAction(Function<Object[], Boolean> doAction) {
        this.doAction = doAction;

        return this;
    }

    @Override
    public Boolean invoke(Object... args) {


        return doAction.apply(args);
    }
}
