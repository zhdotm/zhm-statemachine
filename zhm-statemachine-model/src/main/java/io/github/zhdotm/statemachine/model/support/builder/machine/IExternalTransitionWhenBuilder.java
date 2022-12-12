package io.github.zhdotm.statemachine.model.support.builder.machine;

import java.util.function.Function;

/**
 * @author zhihao.mao
 */

public interface IExternalTransitionWhenBuilder<S, E, C, A> {

    IExternalTransitionPerformBuilder<S, E, C, A> perform(A actionId, Function<Object[], Object> doSomething);
}
