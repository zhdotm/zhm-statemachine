package io.github.zhdotm.statemachine.model.exception.util;

import lombok.SneakyThrows;

public class ExceptionUtil {

    @SneakyThrows
    public static void isTrue(Boolean isSuccess, Class<? extends Exception> clazz, String format, Object... args) {
        if (!isSuccess) {
            throw clazz.getConstructor(String.class).newInstance(String.format(format, args));
        }
    }

}
