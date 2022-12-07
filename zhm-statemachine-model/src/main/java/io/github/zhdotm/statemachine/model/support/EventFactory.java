package io.github.zhdotm.statemachine.model.support;

import io.github.zhdotm.statemachine.model.support.builder.event.IEventBuilder;
import io.github.zhdotm.statemachine.model.support.builder.event.impl.EventBuilderImpl;

/**
 * @author zhihao.mao
 */

public class EventFactory {

    public static <E> IEventBuilder<E> create() {

        return EventBuilderImpl.getInstance();
    }

}
