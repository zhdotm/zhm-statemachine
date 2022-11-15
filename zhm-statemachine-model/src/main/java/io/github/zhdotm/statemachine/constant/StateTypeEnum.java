package io.github.zhdotm.statemachine.constant;


/**
 * 状态类型
 *
 * @author zhihao.mao
 */

public enum StateTypeEnum {

    /**
     * 状态类型
     */
    NORMAL("normal", "普通状态类型, 仍处于一个状态机内"),
    BRIDGE("bridge", "桥类型, 连接两个状态机"),
    ;

    /**
     * 代码
     */
    private final String value;

    /**
     * 描述
     */
    private final String description;

    StateTypeEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

}
