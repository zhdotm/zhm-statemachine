package io.github.zhdotm.statemachine.exception.util;

import lombok.SneakyThrows;

public class ExceptionUtil {

    @SneakyThrows
    public static void isTrue(Boolean isSuccess, Class<? extends Exception> clazz, String message) {
        if (!isSuccess) {
            throw clazz.getConstructor(String.class).newInstance(message);
        }
    }

}
