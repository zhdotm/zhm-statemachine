package io.github.zhdotm.statemachine.domain;


/**
 * @author zhihao.mao
 */

public interface ICondition<S, E, C> {

    /**
     * 获取条件ID
     *
     * @return 条件ID
     */
    C getConditionId();

    /**
     * 是否满足条件
     *
     * @param eventContext 事件上下文
     * @return 出参
     */
    Boolean isSatisfied(IEventContext<S, E> eventContext);

}
