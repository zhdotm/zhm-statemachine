package io.github.zhdotm.statemachine.support;


import io.github.zhdotm.statemachine.constant.StateTypeEnum;
import io.github.zhdotm.statemachine.constant.TransitionTypeEnum;
import io.github.zhdotm.statemachine.domain.*;
import io.github.zhdotm.statemachine.exception.BizStateMachineException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 状态机支持
 *
 * @author zhihao.mao
 */

public interface IStateMachineSupport {

    /**
     * 通过状态机ID, 获取状态机
     *
     * @param stateMachineId 状态机ID
     * @return 状态机
     */
    IStateMachine getStateMachineById(String stateMachineId);

    /**
     * 新增状态机
     *
     * @param stateMachine 状态机
     */
    void putStateMachine(IStateMachine stateMachine);

    /**
     * 构建状态机
     *
     * @param stateMachineId 状态机ID
     * @param transition     状态转换
     * @return 状态机
     * @throws BizStateMachineException 业务异常
     */
    default IStateMachine build(String stateMachineId, ITransition transition) throws BizStateMachineException {

        return build(stateMachineId, new ArrayList<ITransition>() {
            {
                add(transition);
            }
        });
    }

    /**
     * 构建状态机
     *
     * @param stateMachineId 状态机ID
     * @param transitions    状态转换
     * @return 状态机
     * @throws BizStateMachineException 业务异常
     */
    default IStateMachine build(String stateMachineId, List<ITransition> transitions) throws BizStateMachineException {
        if (null == stateMachineId || "".equalsIgnoreCase(stateMachineId)) {

            throw new BizStateMachineException("构建状态机失败: 状态机ID不能为空");
        }

        if (null == transitions || transitions.size() == 0) {

            throw new BizStateMachineException("构建状态机失败: 状态转换不能为空");
        }

        for (ITransition transition : transitions) {
            checkTransition(stateMachineId, transition);
        }

        Map<IState, List<ITransition>> stateTransitionListMap = transitions
                .stream()
                .collect(Collectors.groupingBy(ITransition::getCurrentState));
        Set<IState> stateSet = new HashSet<>(stateTransitionListMap.keySet());
        Set<String> stateIdSet = stateSet
                .stream()
                .map(IState::getStateId)
                .collect(Collectors.toSet());
        stateTransitionListMap.values().forEach(transitionList -> {
            transitionList.forEach(transition -> {
                IState nextState = transition.getNextState();
                if (nextState != null) {
                    if (stateIdSet.add(nextState.getStateId())) {
                        stateSet.add(nextState);
                    }
                }
            });
        });

        List<IState> stateList = new ArrayList<IState>() {
            {
                addAll(stateSet);
            }
        };
        Map<String, IState> stateIdStateMap = stateList
                .stream()
                .collect(Collectors.toMap(IState::getStateId, iState -> iState));

        return new IStateMachine() {

            @Override
            public String getStateMachineId() {

                return stateMachineId;
            }

            @Override
            public Collection<IState> getStates() {

                return stateList;
            }

            @Override
            public IState getStateById(String stateId) {

                return stateIdStateMap.get(stateId);
            }

            @Override
            public Collection<ITransition> getTransitions(IState state) {
                String stateId = state.getStateId();
                IState stateOld = stateIdStateMap.get(stateId);

                return stateTransitionListMap.get(stateOld);
            }
        };
    }

    /**
     * 检查转换的完整性
     *
     * @param stateMachineId 状态机ID
     * @param transition     转换
     * @throws BizStateMachineException 业务异常
     */
    default void checkTransition(String stateMachineId, ITransition transition) throws BizStateMachineException {
        if (transition == null) {

            return;
        }

        String stateMachineIdTemp = transition.getStateMachineId();
        if (null == stateMachineIdTemp || "".equalsIgnoreCase(stateMachineIdTemp)) {

            throw new BizStateMachineException(String.format("检查转换完整性失败: 转换未指定对应状态机ID, stateMachine[%s], transition[%s]", stateMachineId, transition.getTransitionId()));
        }

        if (!stateMachineIdTemp.equalsIgnoreCase(stateMachineId)) {

            throw new BizStateMachineException(String.format("检查转换完整性失败: 转换的状态机ID与输入的状态机ID不一致, stateMachine[%s], transition[%s], transitionStateMachineId[%s]", stateMachineId, transition.getTransitionId(), stateMachineIdTemp));
        }

        String transitionId = transition.getTransitionId();
        if (transitionId == null || "".equalsIgnoreCase(transitionId)) {

            throw new BizStateMachineException(String.format("检查转换完整性失败: 转换ID为空, stateMachine[%s], transition[%s]", stateMachineId, transition.getTransitionId()));
        }

        TransitionTypeEnum type = transition.getType();
        if (type == null) {

            throw new BizStateMachineException(String.format("检查转换完整性失败: 转换类型为空, stateMachine[%s], transition[%s]", stateMachineId, transition.getTransitionId()));
        }

        ICondition condition = transition.getCondition();
        if (condition == null) {

            throw new BizStateMachineException(String.format("检查转换完整性失败: 转换条件为空, stateMachine[%s], transition[%s]", stateMachineId, transition.getTransitionId()));
        }

        IAction action = transition.getAction();
        if (action == null) {

            throw new BizStateMachineException(String.format("检查转换完整性失败: 转换动作为空, stateMachine[%s], transition[%s]", stateMachineId, transition.getTransitionId()));
        }

        IState currentState = transition.getCurrentState();
        if (currentState == null) {

            throw new BizStateMachineException(String.format("检查转换完整性失败: 转换的初始状态为空, stateMachine[%s], transition[%s]", stateMachineId, transition.getTransitionId()));
        }

        stateMachineIdTemp = currentState.getStateMachineId();
        if (stateMachineIdTemp == null || "".equalsIgnoreCase(stateMachineIdTemp)) {

            throw new BizStateMachineException(String.format("检查转换完整性失败: 转换的初始状态未指定状态机ID, stateMachine[%s], transition[%s]", stateMachineId, transition.getTransitionId()));
        }

        if (!stateMachineIdTemp.equalsIgnoreCase(stateMachineId)) {

            throw new BizStateMachineException(String.format("检查转换完整性失败: 转换的初始化状态机ID与输入的状态机ID不一致, stateMachine[%s], transition[%s], transitionStateMachineId[%s]", stateMachineId, transition.getTransitionId(), stateMachineIdTemp));
        }

        IState nextState = transition.getNextState();
        if (nextState == null && TransitionTypeEnum.EXTERNAL == type) {

            throw new BizStateMachineException(String.format("检查转换完整性失败: 外部转换类型不能没有下个转换状态, stateMachine[%s], transition[%s]", stateMachineId, transition.getTransitionId()));
        }

        if (nextState != null) {
            stateMachineIdTemp = nextState.getStateMachineId();
            if (stateMachineIdTemp == null || "".equalsIgnoreCase(stateMachineIdTemp)) {

                throw new BizStateMachineException(String.format("检查转换完整性失败: 下个转换状态未指定状态机ID, stateMachine[%s], transition[%s]", stateMachineId, transition.getTransitionId()));
            }

            if (!stateMachineIdTemp.equalsIgnoreCase(stateMachineId)) {

                throw new BizStateMachineException(String.format("检查转换完整性失败: 下个转换状态不属于当前状态机, stateMachine[%s], transition[%s]", stateMachineId, transition.getTransitionId()));
            }

            String nextStateMachineId = nextState.getNextStateMachineId();
            if (!(nextStateMachineId != null && !"".equalsIgnoreCase(nextStateMachineId))
                    && nextState.getType() == StateTypeEnum.BRIDGE) {

                throw new BizStateMachineException(String.format("检查转换完整性失败: 下个转换状态为桥类型状态但未指定下个状态机ID, stateMachine[%s], transition[%s]", stateMachineId, transition.getTransitionId()));
            }
        }

        Integer sortId = transition.getSortId();
        if (sortId == null) {

            throw new BizStateMachineException(String.format("检查转换完整性失败: 转换排序为空, stateMachine[%s], transition[%s]", stateMachineId, transition.getTransitionId()));
        }

    }

}
