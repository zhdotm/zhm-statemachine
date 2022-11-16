package io.github.zhdotm.statemachine.domain;


import io.github.zhdotm.statemachine.annotation.Transition;
import io.github.zhdotm.statemachine.constant.TransitionTypeEnum;
import io.github.zhdotm.statemachine.exception.BizStateMachineException;

/**
 * 转换
 *
 * @author zhihao.mao
 */

public interface ITransition {

    /**
     * 转换类型
     *
     * @param transitionTypeEnum 转换类型
     * @return this
     */
    ITransition type(TransitionTypeEnum transitionTypeEnum);

    /**
     * 来源状态
     *
     * @param state 来源状态
     * @return this
     */
    ITransition from(IState state);

    /**
     * 前往状态
     *
     * @param state 前往状态
     * @return this
     */
    ITransition to(IState state);

    /**
     * 条件
     *
     * @param condition 条件
     * @return this
     */
    ITransition condition(ICondition condition);

    /**
     * 动作
     *
     * @param action 动作
     * @return this
     */
    ITransition action(IAction action);

    /**
     * 排序号
     *
     * @param sortId 排序号
     * @return this
     */
    ITransition sortId(Integer sortId);

    /**
     * 获取转换ID
     *
     * @return 转换ID
     */
    String getTransitionId();

    /**
     * 获取状态机ID
     *
     * @param stateMachineId 状态机ID
     */
    void setStateMachineId(String stateMachineId);

    /**
     * 获取状态机ID
     *
     * @return 状态机ID
     */
    String getStateMachineId();

    /**
     * 获取状态机ID
     *
     * @return 状态机ID
     */
    default String getStateMachineIdWithAnnotation() {
        String stateMachineId = getStateMachineId();
        if (null != stateMachineId && !"".equalsIgnoreCase(stateMachineId)) {

            return stateMachineId;
        }
        Class<? extends ITransition> clazz = this.getClass();
        Transition transition = clazz.getAnnotation(Transition.class);

        return transition == null ? null : transition.stateMachineId();
    }

    /**
     * 获取排序ID(从小到大)
     *
     * @return 排序ID
     */
    Integer getSortId();

    /**
     * 获取转换类型
     *
     * @return 转换类型
     */
    TransitionTypeEnum getType();

    /**
     * 获取条件
     *
     * @return 条件
     */
    ICondition getCondition();

    /**
     * 获取动作
     *
     * @return 动作
     */
    IAction getAction();

    /**
     * 获取当前状态
     *
     * @return 触发转换的状态
     */
    IState getCurrentState();

    /**
     * 获取下个状态
     *
     * @return 新状态
     */
    IState getNextState();

    /**
     * 转换
     *
     * @param args 动作参数
     * @return 下个状态
     * @throws BizStateMachineException 状态机业务异常
     */
    default IState transfer(Object... args) throws BizStateMachineException {
        TransitionTypeEnum transitionType = getType();
        IAction action = getAction();
        Boolean invokeIsSuccess = action.invoke(args);
        if (!invokeIsSuccess) {

            throw new BizStateMachineException(String.format("transition[%s]执行失败: action[%s]执行失败", getTransitionId(), action.getActionId()));
        }

        return transitionType == TransitionTypeEnum.EXTERNAL ? getNextState() : getCurrentState();
    }

}
