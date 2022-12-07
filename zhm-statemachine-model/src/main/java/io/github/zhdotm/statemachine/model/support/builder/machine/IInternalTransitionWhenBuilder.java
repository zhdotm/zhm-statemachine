package io.github.zhdotm.statemachine.model.support.builder.machine;

import java.util.function.Function;

/**
 * @author zhihao.mao
 */

public interface IInternalTransitionWhenBuilder<S, E, C, A> {

    void perform(A actionId, Function<Object[], Object> execute);
}
