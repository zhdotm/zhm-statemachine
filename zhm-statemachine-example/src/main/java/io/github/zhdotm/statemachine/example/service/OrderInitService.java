package io.github.zhdotm.statemachine.example.service;

import io.github.zhdotm.statemachine.model.constant.TransitionTypeEnum;
import io.github.zhdotm.statemachine.starter.web.adapter.ITransitionAdapter;
import io.github.zhdotm.statemachine.starter.web.annotation.StateMachineAction;
import io.github.zhdotm.statemachine.starter.web.annotation.StateMachineComponent;
import io.github.zhdotm.statemachine.starter.web.annotation.StateMachineCondition;
import org.springframework.stereotype.Component;

/**
 * 初始化
 *
 * @author zhihao.mao
 */

@Component
@StateMachineComponent(
        stateMachineId = "RENT_ORDER",
        type = TransitionTypeEnum.EXTERNAL,
        from = {"STATE_WAIT_INIT"},
        on = "EVENT_INIT",
        to = "STATE_WAIT_PROMO"
)
public class OrderInitService implements ITransitionAdapter {

    @StateMachineCondition(conditionId = "IS_ABLE_INIT")
    public Boolean check(String orderId, String buyerId, String commodityId, Integer quantity, Long unitPrice) {
        System.out.println("检查能否初始化订单");

        return Boolean.TRUE;
    }

    @StateMachineAction(acitonId = "ACTION_INIT")
    public String execute(String orderId, String buyerId, String commodityId, Integer quantity, Long unitPrice) {
        System.out.println("执行初始化订单动作");
        System.out.printf("订单ID[%s], 购买人ID[%s], 商品ID[%s], 数量[%s], 单价[%s]%n", orderId, buyerId, commodityId, quantity, unitPrice);

        return "执行初始化订单" + orderId + "结束";
    }

}
