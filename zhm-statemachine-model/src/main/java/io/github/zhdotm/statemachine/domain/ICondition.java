package io.github.zhdotm.statemachine.domain;


/**
 * 条件
 *
 * @author zhihao.mao
 */

public interface ICondition<S, E> {

    /**
     * 是否满足条件
     *
     * @param eventContext 事件上下文
     * @return 出参
     */
    Boolean isSatisfied(IEventContext<S, E> eventContext);

}
