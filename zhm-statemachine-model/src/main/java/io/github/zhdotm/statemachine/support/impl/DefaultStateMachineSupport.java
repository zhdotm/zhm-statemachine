package io.github.zhdotm.statemachine.support.impl;


import io.github.zhdotm.statemachine.domain.IStateMachine;
import io.github.zhdotm.statemachine.support.IStateMachineSupport;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认状态机支持, 默认用hashmap缓存状态机信息
 *
 * @author zhihao.mao
 */

public class DefaultStateMachineSupport implements IStateMachineSupport {

    private final Map<String, IStateMachine> idStateMachineMap = new ConcurrentHashMap<>();

    private static volatile DefaultStateMachineSupport stateMachineSupport;

    public static DefaultStateMachineSupport getInstance() {
        if (stateMachineSupport == null) {
            synchronized (DefaultStateMachineSupport.class) {
                if (stateMachineSupport == null) {
                    stateMachineSupport = new DefaultStateMachineSupport();
                    return stateMachineSupport;
                }
            }
        }

        return stateMachineSupport;
    }

    @Override
    public IStateMachine getStateMachineById(String stateMachineId) {

        return idStateMachineMap.get(stateMachineId);
    }

    @Override
    public void putStateMachine(IStateMachine stateMachine) {
        idStateMachineMap.put(stateMachine.getStateMachineId(), stateMachine);
    }

    private DefaultStateMachineSupport() {
    }

}
