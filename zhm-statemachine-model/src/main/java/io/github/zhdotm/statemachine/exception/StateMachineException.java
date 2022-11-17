package io.github.zhdotm.statemachine.exception;

import lombok.Getter;

/**
 * 状态机业务异常
 *
 * @author zhihao.mao
 */

public class StateMachineException extends Exception {

    @Getter
    private final String code;

    @Getter
    private final String message;

    public StateMachineException(String message) {
        this.code = "-1";
        this.message = message;
    }

}
