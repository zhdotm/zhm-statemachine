package io.github.zhdotm.statemachine.example.service;

import io.github.zhdotm.statemachine.model.constant.TransitionTypeEnum;
import io.github.zhdotm.statemachine.starter.web.adapter.ITransitionAdapter;
import io.github.zhdotm.statemachine.starter.web.annotation.StateMachineAction;
import io.github.zhdotm.statemachine.starter.web.annotation.StateMachineComponent;
import io.github.zhdotm.statemachine.starter.web.annotation.StateMachineCondition;
import org.springframework.stereotype.Component;

/**
 * 取消
 *
 * @author zhihao.mao
 */

@Component
@StateMachineComponent(stateMachineId = "RENT_ORDER",
        type = TransitionTypeEnum.EXTERNAL,
        from = {"STATE_WAIT_INIT", "STATE_WAIT_PROMO", "STATE_WAIT_BALANCE", "STATE_WAIT_PAY"},
        on = "EVENT_CANCEL",
        to = "STATE_CANCELED")
public class OrderCancelService implements ITransitionAdapter {

    @StateMachineCondition(conditionId = "IS_ABLE_CANCEL")
    public Boolean check(String orderId) {
        System.out.println("检查能否取消订单");

        return Boolean.TRUE;
    }

    @StateMachineAction(acitonId = "ACTION_CANCEL")
    public String execute(String orderId) {
        System.out.println("执行取消订单动作");
        System.out.println("取消订单: " + orderId);

        return "取消订单" + orderId + "执行结束";
    }

}
