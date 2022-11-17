package io.github.zhdotm.statemachine.domain;


/**
 * 动作
 *
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
    Boolean invoke(Object... args);

}
