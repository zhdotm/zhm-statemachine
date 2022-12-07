package io.github.zhdotm.statemachine.example.service;

import io.github.zhdotm.statemachine.model.constant.TransitionTypeEnum;
import io.github.zhdotm.statemachine.starter.web.adapter.ITransitionAdapter;
import io.github.zhdotm.statemachine.starter.web.annotation.StateMachineAction;
import io.github.zhdotm.statemachine.starter.web.annotation.StateMachineComponent;
import io.github.zhdotm.statemachine.starter.web.annotation.StateMachineCondition;
import org.springframework.stereotype.Component;

/**
 * 支付
 *
 * @author zhihao.mao
 */

@Component
@StateMachineComponent(
        stateMachineId = "RENT_ORDER",
        type = TransitionTypeEnum.EXTERNAL,
        from = {"STATE_WAIT_PAY"},
        on = "EVENT_PAY",
        to = "STATE_WAIT_BOOKING"
)
public class OrderPayService implements ITransitionAdapter {

    @StateMachineCondition(conditionId = "IS_ABLE_PAY")
    public Boolean check(String arg) {
        System.out.println(getCurrentState() + ": 检查能否关闭订单");

        return Boolean.TRUE;
    }

    @StateMachineAction(actionId = "ACTION_PAY")
    public String execute(String arg) {
        System.out.println(getCurrentState() + ": 执行关闭订单动作");

        return "执行结束";
    }

}
