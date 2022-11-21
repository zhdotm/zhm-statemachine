package io.github.zhdotm.statemachine.example.service;

import io.github.zhdotm.statemachine.model.constant.TransitionTypeEnum;
import io.github.zhdotm.statemachine.starter.web.adapter.ITransitionAdapter;
import io.github.zhdotm.statemachine.starter.web.annotation.StateMachineAction;
import io.github.zhdotm.statemachine.starter.web.annotation.StateMachineComponent;
import io.github.zhdotm.statemachine.starter.web.annotation.StateMachineCondition;
import org.springframework.stereotype.Component;

/**
 * 记账
 *
 * @author zhihao.mao
 */

@Component
@StateMachineComponent(
        stateMachineId = "RENT_ORDER",
        type = TransitionTypeEnum.EXTERNAL,
        from = {"STATE_WAIT_BOOKING"},
        on = "EVENT_BOOKING",
        to = "STATE_FINISHED"
)
public class OrderBookingService implements ITransitionAdapter {

    @StateMachineCondition(conditionId = "IS_ABLE_BOOKING")
    public Boolean check(String orderId) {
        System.out.println("检查能否记账订单");

        return Boolean.TRUE;
    }

    @StateMachineAction(actionId = "ACTION_BOOKING")
    public String execute(String orderId) {
        System.out.println("执行订单记账动作");
        System.out.println("订单" + orderId + "执行订单记账动作");

        return "执行结算订单" + orderId + "结束";
    }

}
