package io.github.zhdotm.statemachine.constant;


/**
 * 转换类型
 *
 * @author zhihao.mao
 */

public enum TransitionTypeEnum {

    /**
     * 转换类型
     */
    INTERNAL("internal", "内部转换, 无状态转换"),
    EXTERNAL("external", "外部转换, 有状态转换"),
    ;

    /**
     * 代码
     */
    private final String value;

    /**
     * 描述
     */
    private final String description;

    TransitionTypeEnum(String value, String description) {
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
