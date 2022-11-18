package io.github.zhdotm.statemachine.domain;


/**
 * @author zhihao.mao
 */

@FunctionalInterface
public interface ICondition<S, E> {

    /**
     * 是否满足条件
     *
     * @param eventContext 事件上下文
     * @return 出参
     */
    Boolean isSatisfied(IEventContext<S, E> eventContext);

}
