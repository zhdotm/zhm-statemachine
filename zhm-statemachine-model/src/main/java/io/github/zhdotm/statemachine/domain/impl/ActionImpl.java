package io.github.zhdotm.statemachine.domain.impl;

import io.github.zhdotm.statemachine.domain.IAction;
import lombok.Getter;

import java.util.function.Function;

/**
 * @author zhihao.mao
 */

public class ActionImpl<A> implements IAction<A> {

    @Getter
    private A actionId;

    private Function<Object[], Object> execute;

    public static <A> ActionImpl<A> getInstance() {

        return new ActionImpl<>();
    }

    public ActionImpl<A> actionId(A actionId) {
        this.actionId = actionId;

        return this;
    }

    public ActionImpl<A> execute(Function<Object[], Object> execute) {
        this.execute = execute;

        return this;
    }

    @Override
    public Object invoke(Object... args) {


        return execute.apply(args);
    }
}
