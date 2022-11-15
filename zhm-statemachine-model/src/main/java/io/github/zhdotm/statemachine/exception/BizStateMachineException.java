package io.github.zhdotm.statemachine.exception;

/**
 * 状态机业务异常
 *
 * @author zhihao.mao
 */

public class BizStateMachineException extends Exception {

    private final String code;
    private final String message;

    public BizStateMachineException(String message) {
        this.code = "-1";
        this.message = message;
    }

    @Override
    public String getMessage() {

        return String.format("状态机业务异常[%s]: %s", code, message);
    }

}
