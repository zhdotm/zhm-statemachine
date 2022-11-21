package io.github.zhdotm.statemachine.example.service;

import io.github.zhdotm.statemachine.model.constant.TransitionTypeEnum;
import io.github.zhdotm.statemachine.starter.web.adapter.ITransitionAdapter;
import io.github.zhdotm.statemachine.starter.web.annotation.StateMachineAction;
import io.github.zhdotm.statemachine.starter.web.annotation.StateMachineComponent;
import io.github.zhdotm.statemachine.starter.web.annotation.StateMachineCondition;
import org.springframework.stereotype.Component;

/**
 * 修改订单金额
 *
 * @author zhihao.mao
 */

@Component
@StateMachineComponent(
        stateMachineId = "RENT_ORDER",
        type = TransitionTypeEnum.INTERNAL,
        from = {"STATE_WAIT_PROMO", "STATE_WAIT_BALANCE", "STATE_WAIT_PAY"},
        on = "EVENT_MODIFY_PRICE"
)
public class OrderModifyPriceService implements ITransitionAdapter {

    @StateMachineCondition(conditionId = "IS_ABLE_MODIFY_PRICE")
    public Boolean check(String orderId, Long price) {
        System.out.println("检查能否修改订单金额");

        return Boolean.TRUE;
    }

    @StateMachineAction(actionId = "ACTION_MODIFY_PRICE")
    public String execute(String orderId, Long price) {
        System.out.println("执行修改订单金额动作");
        System.out.println("修改订单金额: 将" + orderId + "的订单金额修改为" + price);

        return "执行修改订单" + orderId + "金额为" + price + "结束";
    }

}
