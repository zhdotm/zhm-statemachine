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

public class StateMachineBuilderImpl<M, S, E, A> implements StateMachineBuilder<M, S, E, A> {

    private final StateMachineImpl<M, S, E, A> stateMachine = StateMachineImpl.getInstance();

    private final List<ITransition<S, E, A>> transitions = new ArrayList<>();

    public static <M, S, E, A> StateMachineBuilderImpl<M, S, E, A> getInstance() {

        return new StateMachineBuilderImpl<>();
    }

    @Override
    public ExternalTransitionBuilder<S, E, A> createExternalTransition() {
        TransitionImpl<S, E, A> transition = TransitionImpl.getInstance();
        transition.type(TransitionTypeEnum.EXTERNAL);
        transitions.add(transition);

        return ExternalTransitionBuilderImpl.getInstance(transition);
    }

    @Override
    public InternalTransitionBuilder<S, E, A> createInternalTransition() {
        TransitionImpl<S, E, A> transition = TransitionImpl.getInstance();
        transition.type(TransitionTypeEnum.INTERNAL);
        transitions.add(transition);

        return InternalTransitionBuilderImpl.getInstance(transition);
    }

    @Override
    public IStateMachine<M, S, E, A> build(M stateMachineId) {
        stateMachine.stateMachineId(stateMachineId);
        stateMachine.addTransitions(transitions);

        return stateMachine;
    }

}