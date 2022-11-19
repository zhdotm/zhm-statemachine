package io.github.zhdotm.statemachine.model.log;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProcessLog {

    public static void debug(String format, Object... args) {

        log.debug(String.format(format, args));
    }

    public static void info(String format, Object... args) {

        log.info(String.format(format, args));
    }

    public static void warn(String format, Object... args) {

        log.warn(String.format(format, args));
    }

    public static void error(String format, Object... args) {

        log.error(String.format(format, args));
    }
}
