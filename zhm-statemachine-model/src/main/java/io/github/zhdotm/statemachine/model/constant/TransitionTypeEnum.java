package io.github.zhdotm.statemachine.model.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhihao.mao
 */

@AllArgsConstructor
public enum TransitionTypeEnum {
    /**
     * 转换类型
     */
    EXTERNAL("external", "外部转换"),
    INTERNAL("internal", "内部转换"),
    ;

    @Getter
    private final String code;

    @Getter
    private final String description;

}
