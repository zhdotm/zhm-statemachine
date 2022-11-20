package io.github.zhdotm.statemachine.example.service;

import io.github.zhdotm.statemachine.model.constant.TransitionTypeEnum;
import io.github.zhdotm.statemachine.starter.web.adapter.ITransitionAdapter;
import io.github.zhdotm.statemachine.starter.web.annotation.StateMachineAction;
import io.github.zhdotm.statemachine.starter.web.annotation.StateMachineComponent;
import io.github.zhdotm.statemachine.starter.web.annotation.StateMachineCondition;
import org.springframework.stereotype.Component;

/**
 * 选择支付方式
 *
 * @author zhihao.mao
 */

@Component
@StateMachineComponent(
        stateMachineId = "RENT_ORDER",
        type = TransitionTypeEnum.INTERNAL,
        from = {"STATE_WAIT_PAY"},
        on = "EVENT_CHOOSE_PAY_WAY"
)
public class OrderChoosePayWayService implements ITransitionAdapter {

    @StateMachineCondition(conditionId = "IS_ABLE_CHOOSE_PAY_WAY")
    public Boolean check(String orderId, String payWay) {
        System.out.println("检查能否选择支付订单方式");

        return Boolean.TRUE;
    }

    @StateMachineAction(acitonId = "ACTION_CHOOSE_PAY_WAY")
    public String execute(String orderId, String payWay) {
        System.out.println("执行选择订单" + orderId + "支付方式" + payWay + "动作");

        return "执行订单" + orderId + "选择支付方式" + payWay + "动作结束";
    }

}
