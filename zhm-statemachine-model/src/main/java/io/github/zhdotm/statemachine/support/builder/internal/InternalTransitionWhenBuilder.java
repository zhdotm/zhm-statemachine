package io.github.zhdotm.statemachine.support.builder.internal;

import java.util.function.Function;

/**
 * @author zhihao.mao
 */

public interface InternalTransitionWhenBuilder<S, E, A> {

    void perform(A actionId, Function<Object[], Boolean> doSomething);
}