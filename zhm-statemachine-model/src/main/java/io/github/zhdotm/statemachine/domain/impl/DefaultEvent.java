package io.github.zhdotm.statemachine.domain.impl;

import io.github.zhdotm.statemachine.domain.IEvent;

/**
 * 默认事件
 *
 * @author zhihao.mao
 */

public class DefaultEvent implements IEvent {

    private String eventId;

    private Object[] payload;

    public DefaultEvent(String eventId, Object[] payload) {
        this.eventId = eventId;
        this.payload = payload;
    }

    public DefaultEvent() {
    }

    public static DefaultEvent getInstance(String eventId, Object[] payload) {

        return new DefaultEvent(eventId, payload);
    }

    public static DefaultEvent getInstance() {

        return new DefaultEvent();
    }

    public DefaultEvent eventId(String eventId) {
        this.eventId = eventId;

        return this;
    }

    public DefaultEvent payload(Object[] payload) {
        this.payload = payload;

        return this;
    }

    @Override
    public String getEventId() {
        return eventId;
    }

    @Override
    public Object[] getPayload() {
        return payload;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setPayload(Object[] payload) {
        this.payload = payload;
    }

}
