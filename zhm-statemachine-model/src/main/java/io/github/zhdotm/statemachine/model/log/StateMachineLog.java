package io.github.zhdotm.statemachine.model.log;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhihao.mao
 */

@Slf4j
public class StateMachineLog {

    public static String tail(String content, Integer length, String end) {

        return tail(content, length, "-", end);
    }

    public static String tail(String content, Integer length, String pad, String end) {
        int contentLength = content.length();
        int endLength = end.length();
        if (contentLength + endLength >= length) {

            return content + end;
        }

        StringBuilder sb = new StringBuilder(content);
        for (long i = 0; i < (length - (contentLength + endLength)); i++) {
            sb.append(pad);
        }

        return sb.append(end).toString();
    }

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
