package io.github.zhdotm.statemachine.example.service;

import io.github.zhdotm.statemachine.model.constant.TransitionTypeEnum;
import io.github.zhdotm.statemachine.starter.web.adapter.ITransitionAdapter;
import io.github.zhdotm.statemachine.starter.web.annotation.StateMachineAction;
import io.github.zhdotm.statemachine.starter.web.annotation.StateMachineComponent;
import io.github.zhdotm.statemachine.starter.web.annotation.StateMachineCondition;
import org.springframework.stereotype.Component;

/**
 * 营销
 *
 * @author zhihao.mao
 */

@Component
@StateMachineComponent(
        stateMachineId = "RENT_ORDER",
        type = TransitionTypeEnum.EXTERNAL,
        from = {"STATE_WAIT_PROMO"},
        on = "EVENT_PROMO",
        to = "STATE_WAIT_BALANCE"
)
public class OrderPromoService implements ITransitionAdapter {

    @StateMachineCondition(conditionId = "IS_ABLE_PROMO")
    public Boolean check(String orderId, String couponId) {
        System.out.println("检查能否营销订单");

        return Boolean.TRUE;
    }

    @StateMachineAction(actionId = "ACTION_PROMO")
    public String execute(String orderId, String couponId) {
        System.out.println("执行营销订单动作");
        System.out.printf("订单[%s]使用优惠券[%s]%n", orderId, couponId);

        return "执行营销订单" + orderId + "动作结束";
    }

}
