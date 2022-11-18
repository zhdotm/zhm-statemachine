package io.github.zhdotm.statemachine.support.builder;


import io.github.zhdotm.statemachine.constant.TransitionTypeEnum;
import io.github.zhdotm.statemachine.domain.IStateMachine;
import io.github.zhdotm.statemachine.domain.ITransition;
import io.github.zhdotm.statemachine.domain.impl.StateMachineImpl;
import io.github.zhdotm.statemachine.domain.impl.TransitionImpl;
import io.github.zhdotm.statemachine.support.builder.external.ExternalTransitionBuilder;
import io.github.zhdotm.statemachine.support.builder.external.impl.ExternalTransitionBuilderImpl;
import io.github.zhdotm.statemachine.support.builder.internal.InternalTransitionBuilder;
import io.github.zhdotm.statemachine.support.builder.internal.impl.InternalTransitionBuilderImpl;

import java.util.ArrayList;
import java.util.List;


/**
 * @author zhihao.mao
 */

public class StateMachineBuilderImpl<M, S, E, C, A> implements StateMachineBuilder<M, S, E, C, A> {

    private final StateMachineImpl<M, S, E, C, A> stateMachine = StateMachineImpl.getInstance();

    private final List<ITransition<S, E, C, A>> transitions = new ArrayList<>();

    public static <M, S, E, C, A> StateMachineBuilderImpl<M, S, E, C, A> getInstance() {

        return new StateMachineBuilderImpl<>();
    }

    @Override
    public ExternalTransitionBuilder<S, E, C, A> createExternalTransition() {
        TransitionImpl<S, E, C, A> transition = TransitionImpl.getInstance();
        transition.type(TransitionTypeEnum.EXTERNAL);
        transitions.add(transition);

        return ExternalTransitionBuilderImpl.getInstance(transition);
    }

    @Override
    public InternalTransitionBuilder<S, E, C, A> createInternalTransition() {
        TransitionImpl<S, E, C, A> transition = TransitionImpl.getInstance();
        transition.type(TransitionTypeEnum.INTERNAL);
        transitions.add(transition);

        return InternalTransitionBuilderImpl.getInstance(transition);
    }

    @Override
    public IStateMachine<M, S, E, C, A> build(M stateMachineId) {
        stateMachine.stateMachineId(stateMachineId);
        stateMachine.addTransitions(transitions);

        return stateMachine;
    }

}
