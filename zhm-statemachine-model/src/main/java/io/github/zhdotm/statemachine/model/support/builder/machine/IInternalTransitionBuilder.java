package io.github.zhdotm.statemachine.model.support.builder.machine;


/**
 * @author zhihao.mao
 */

public interface IInternalTransitionBuilder<S, E, C, A> {

    IInternalTransitionBuilder<S, E, C, A> sort(Integer sort);

    IInternalTransitionFromBuilder<S, E, C, A> from(S... stateIds);
}
