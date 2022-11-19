package io.github.zhdotm.statemachine.model.domain.impl;

import io.github.zhdotm.statemachine.model.domain.IAction;
import lombok.Getter;
import lombok.NonNull;

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

    public ActionImpl<A> actionId(@NonNull A actionId) {
        this.actionId = actionId;

        return this;
    }

    public ActionImpl<A> execute(@NonNull Function<Object[], Object> execute) {
        this.execute = execute;

        return this;
    }

    @Override
    public Object invoke(Object... args) {


        return execute.apply(args);
    }
}
