package io.github.zhdotm.statemachine.model.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhihao.mao
 */

@AllArgsConstructor
public enum CharacterEnum {

    /**
     * 符号
     */
    HASH_TAG("#"),
    EMPTY(""),
    ;

    @Getter
    private final String value;

}
