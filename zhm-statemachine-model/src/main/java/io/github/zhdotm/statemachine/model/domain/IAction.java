package io.github.zhdotm.statemachine.model.domain;


/**
 * @author zhihao.mao
 */

public interface IAction<A> {

    /**
     * 获取动作ID
     *
     * @return 动作ID
     */
    A getActionId();

    /**
     * 执行动作
     *
     * @param args 参数
     * @return 是否执行成功
     */
    Object invoke(Object... args);

}
