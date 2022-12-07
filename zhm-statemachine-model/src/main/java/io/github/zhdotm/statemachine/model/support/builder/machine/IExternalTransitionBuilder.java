package io.github.zhdotm.statemachine.model.support.builder.machine;


/**
 * @author zhihao.mao
 */

public interface IExternalTransitionBuilder<S, E, C, A> {

    IExternalTransitionBuilder<S, E, C, A> sort(Integer sort);

    IExternalTransitionFromBuilder<S, E, C, A> from(S... stateIds);
}
