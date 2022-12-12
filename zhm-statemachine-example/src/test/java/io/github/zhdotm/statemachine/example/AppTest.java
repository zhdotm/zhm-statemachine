package io.github.zhdotm.statemachine.example;

import com.alibaba.fastjson.JSON;
import io.github.zhdotm.statemachine.model.domain.IEvent;
import io.github.zhdotm.statemachine.model.domain.IEventContext;
import io.github.zhdotm.statemachine.model.domain.IStateContext;
import io.github.zhdotm.statemachine.model.support.EventContextFactory;
import io.github.zhdotm.statemachine.model.support.EventFactory;
import io.github.zhdotm.statemachine.model.support.builder.context.event.IEventContextBuilder;
import io.github.zhdotm.statemachine.model.support.builder.event.IEventBuilder;
import io.github.zhdotm.statemachine.starter.web.support.StateMachineSupport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zhihao.mao
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTest {

    /**
     * 关闭订单
     */
    @Test
    public void close() {

        IEventContextBuilder<String, String> eventContextBuilder = EventContextFactory.create();
        IEventBuilder<String> eventBuilder = EventFactory.create();
        IEvent<String> event = eventBuilder
                .payload("orderId:123456789")
                .id("EVENT_CLOSE")
                .build();
        IEventContext<String, String> eventContext = eventContextBuilder.from("STATE_WAIT_PROMO")
                .on(event)
                .build();

//        IStateContext<String, String> stateContext = StateMachineSupport
//                .fireEvent("RENT_ORDER", "STATE_WAIT_PROMO", "EVENT_CLOSE", "orderId:123456789");

        IStateContext<String, String> stateContext = StateMachineSupport.fireEvent("RENT_ORDER", eventContext);

        System.out.println(JSON.toJSONString(stateContext));
    }

    /**
     * 取消订单
     */
    @Test
    public void cancel() {
        IStateContext<String, String> stateContext = StateMachineSupport
                .fireEvent("RENT_ORDER", "STATE_WAIT_PAY", "EVENT_CANCEL", "orderId:123456789");

        System.out.println(JSON.toJSONString(stateContext));
    }

    /**
     * 初始化订单
     */
    @Test
    public void init() {
        IStateContext<String, String> stateContext = StateMachineSupport
                .fireEvent("RENT_ORDER", "STATE_WAIT_INIT", "EVENT_INIT", "orderId:111111", "buyerId:2222222", "commodityId:333333", 5, 500L);

        System.out.println(JSON.toJSONString(stateContext));
    }

    /**
     * 营销订单
     */
    @Test
    public void promo() {
        IStateContext<String, String> stateContext = StateMachineSupport
                .fireEvent("RENT_ORDER", "STATE_WAIT_PROMO", "EVENT_PROMO", "orderId:111111", "couponId:222222");

        System.out.println(JSON.toJSONString(stateContext));
    }

    /**
     * 修改订单金额
     */
    @Test
    public void modifyPrice() {
        IStateContext<String, String> stateContext = StateMachineSupport
                .fireEvent("RENT_ORDER", "STATE_WAIT_PAY", "EVENT_MODIFY_PRICE", "orderId:111111", 200L);

        System.out.println(JSON.toJSONString(stateContext));
    }

    /**
     * 结算订单
     */
    @Test
    public void balance() {
        IStateContext<String, String> stateContext = StateMachineSupport
                .fireEvent("RENT_ORDER", "STATE_WAIT_BALANCE", "EVENT_BALANCE", "orderId:111111");

        System.out.println(JSON.toJSONString(stateContext));
    }

    /**
     * 支付订单
     */
    @Test
    public void pay() {
        IStateContext<String, String> stateContext = StateMachineSupport
                .fireEvent("RENT_ORDER", "STATE_WAIT_PAY", "EVENT_PAY", "orderId:111111");

        System.out.println(JSON.toJSONString(stateContext));
    }

    /**
     * 记账订单
     */
    @Test
    public void booking() {
        IStateContext<String, String> stateContext = StateMachineSupport
                .fireEvent("RENT_ORDER", "STATE_WAIT_BOOKING", "EVENT_BOOKING", "orderId:111111");

        System.out.println(JSON.toJSONString(stateContext));
    }

}