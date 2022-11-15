package io.github.zhdotm.statemachine.constant;


/**
 * 事件类型
 *
 * @author zhihao.mao
 */

public enum EventTypeEnum {

    /**
     * 事件类型
     */
    NORMAL("normal", "普通事件类型, 既可以用于内部转换又可以用户外部转换"),
    INTERNAL("internal", "内部事件类型, 内部转换"),
    EXTERNAL("external", "外部事件类型, 外部转换(有状态转换)"),
    ;

    /**
     * 代码
     */
    private final String value;

    /**
     * 描述
     */
    private final String description;

    EventTypeEnum(String value, String description) {
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
