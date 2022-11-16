package io.github.zhdotm.statemachine.domain.impl;

import io.github.zhdotm.statemachine.domain.abs.AbstractTransition;

/**
 * 默认转换
 *
 * @author zhihao.mao
 */

public class DefaultTransition extends AbstractTransition {

    private String transitionId;

    public DefaultTransition(String transitionId) {
        this.transitionId = transitionId;
    }

    @Override
    public String getTransitionId() {
        return transitionId;
    }

    public void setTransitionId(String transitionId) {
        this.transitionId = transitionId;
    }
}
