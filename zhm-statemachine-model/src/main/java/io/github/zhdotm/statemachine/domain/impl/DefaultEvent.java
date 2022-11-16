package io.github.zhdotm.statemachine.domain.impl;

import io.github.zhdotm.statemachine.domain.IEvent;

/**
 * 默认事件
 *
 * @author zhihao.mao
 */

public class DefaultEvent implements IEvent {

    private final String eventId;

    private final Object[] payload;

    public DefaultEvent(String eventId, Object[] payload) {
        this.eventId = eventId;
        this.payload = payload;
    }

    @Override
    public String getEventId() {
        return eventId;
    }

    @Override
    public Object[] getPayload() {
        return payload;
    }

}
