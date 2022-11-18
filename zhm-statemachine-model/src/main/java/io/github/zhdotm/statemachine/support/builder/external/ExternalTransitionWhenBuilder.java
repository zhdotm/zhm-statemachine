package io.github.zhdotm.statemachine.support.builder.external;

import java.util.function.Function;

/**
 * @author zhihao.mao
 */

public interface ExternalTransitionWhenBuilder<S, E, A> {

    ExternalTransitionToBuilder<S, E, A> perform(A actionId, Function<Object[], Boolean> doSomething);
}
