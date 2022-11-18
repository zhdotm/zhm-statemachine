package io.github.zhdotm.statemachine.support;


import io.github.zhdotm.statemachine.support.builder.StateMachineBuilder;
import io.github.zhdotm.statemachine.support.builder.StateMachineBuilderImpl;

/**
 * @author zhihao.mao
 */

public class StateMachineFactory {

    public static <M, S, E, A> StateMachineBuilder<M, S, E, A> create() {

        return StateMachineBuilderImpl.getInstance();
    }

}
