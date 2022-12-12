package io.github.zhdotm.statemachine.model.support;

import io.github.zhdotm.statemachine.model.domain.IEvent;
import io.github.zhdotm.statemachine.model.domain.IEventContext;
import io.github.zhdotm.statemachine.model.domain.IStateContext;
import io.github.zhdotm.statemachine.model.domain.IStateMachine;
import io.github.zhdotm.statemachine.model.domain.impl.EventContextImpl;
import io.github.zhdotm.statemachine.model.domain.impl.EventImpl;
import io.github.zhdotm.statemachine.model.support.builder.context.event.IEventContextBuilder;
import io.github.zhdotm.statemachine.model.support.builder.event.IEventBuilder;
import io.github.zhdotm.statemachine.model.support.builder.machine.IStateMachineBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author zhihao.mao
 */

public class StateMachineFactoryTest {

    private IStateMachine<StateMachineEnum, StateEnum, EventEnum, ConditionEnum, ActionEnum> stateMachine = null;

    @Before
    public void build() {
        IStateMachineBuilder<StateMachineEnum, StateEnum, EventEnum, ConditionEnum, ActionEnum> stateMachineBuilder = StateMachineFactory.create();

        //待初始化、待营销、待结算、待支付 -> 关闭
        stateMachineBuilder
                .createExternalTransition()
                .from(StateEnum.STATE_WAIT_INIT,
                        StateEnum.STATE_WAIT_PROMO,
                        StateEnum.STATE_WAIT_BALANCE,
                        StateEnum.STATE_WAIT_PAY)
                .on(EventEnum.EVENT_CLOSE)
                .when(ConditionEnum.IS_ABLE_CLOSE, stateEnumEventEnumIEventContext -> {
                    System.out.println("检查能否关闭订单");
                    IEvent<EventEnum> event = stateEnumEventEnumIEventContext.getEvent();
                    Object[] payload = event.getPayload();
                    System.out.println("收到事件参数负载: " + Arrays.toString(payload));

                    return Boolean.TRUE;
                }).perform(ActionEnum.ACTION_CLOSE, args -> {
            System.out.println("执行关闭订单动作");
            System.out.println("收到事件参数负载: " + Arrays.toString(args));

            return "动作关闭订单执行成功";
        }).to(StateEnum.STATE_CLOSED);

        //待初始化、待营销、待结算、待支付 -> 取消
        stateMachineBuilder
                .createExternalTransition()
                .from(StateEnum.STATE_WAIT_INIT,
                        StateEnum.STATE_WAIT_PROMO,
                        StateEnum.STATE_WAIT_BALANCE,
                        StateEnum.STATE_WAIT_PAY)
                .on(EventEnum.EVENT_CANCEL)
                .when(ConditionEnum.IS_ABLE_CANCEL, stateEnumEventEnumIEventContext -> {
                    System.out.println("检查能否取消订单");
                    IEvent<EventEnum> event = stateEnumEventEnumIEventContext.getEvent();
                    Object[] payload = event.getPayload();
                    System.out.println("收到事件参数负载: " + Arrays.toString(payload));

                    return Boolean.TRUE;
                }).perform(ActionEnum.ACTION_CANCEL, args -> {
            System.out.println("执行取消订单动作");
            System.out.println("收到事件参数负载: " + Arrays.toString(args));

            return "动作取消执行成功";
        }).to(StateEnum.STATE_CANCELED);

        //修改订单金额（待营销、待结算、待支付 ）
        stateMachineBuilder
                .createInternalTransition()
                .from(StateEnum.STATE_WAIT_PROMO,
                        StateEnum.STATE_WAIT_BALANCE,
                        StateEnum.STATE_WAIT_PAY)
                .on(EventEnum.EVENT_MODIFY_PRICE)
                .when(ConditionEnum.IS_ABLE_MODIFY_PRICE, stateEnumEventEnumIEventContext -> {
                    System.out.println("检查能否修改订单金额");
                    IEvent<EventEnum> event = stateEnumEventEnumIEventContext.getEvent();
                    Object[] payload = event.getPayload();
                    System.out.println("收到事件参数负载: " + Arrays.toString(payload));

                    return Boolean.TRUE;
                }).perform(ActionEnum.ACTION_MODIFY_PRICE, args -> {
            System.out.println("执行修改订单金额动作");
            System.out.println("收到事件参数负载: " + Arrays.toString(args));

            return "动作修改订单金额执行成功";
        });

        //待初始化 -> 待营销
        stateMachineBuilder
                .createExternalTransition()
                .from(StateEnum.STATE_WAIT_INIT)
                .on(EventEnum.EVENT_INIT)
                .when(ConditionEnum.IS_ABLE_INIT, stateEnumEventEnumIEventContext -> {
                    System.out.println("检查能否初始化订单");
                    IEvent<EventEnum> event = stateEnumEventEnumIEventContext.getEvent();
                    Object[] payload = event.getPayload();
                    System.out.println("收到事件参数负载: " + Arrays.toString(payload));

                    return Boolean.TRUE;
                }).perform(ActionEnum.ACTION_INIT, args -> {
            System.out.println("执行初始化订单动作");
            System.out.println("收到事件参数负载: " + Arrays.toString(args));

            return "动作初始化执行成功";
        }).to(StateEnum.STATE_WAIT_PROMO);

        //待营销 -> 待结算
        stateMachineBuilder
                .createExternalTransition()
                .from(StateEnum.STATE_WAIT_PROMO)
                .on(EventEnum.EVENT_PROMO)
                .when(ConditionEnum.IS_ABLE_PROMO, stateEnumEventEnumIEventContext -> {
                    System.out.println("检查能否给订单使用优惠券");
                    IEvent<EventEnum> event = stateEnumEventEnumIEventContext.getEvent();
                    Object[] payload = event.getPayload();
                    System.out.println("收到事件参数负载: " + Arrays.toString(payload));

                    return Boolean.TRUE;
                }).perform(ActionEnum.ACTION_PROMO, args -> {
            System.out.println("执行订单营销动作");
            System.out.println("收到事件参数负载: " + Arrays.toString(args));

            return "动作营销订单执行成功";
        }).to(StateEnum.STATE_WAIT_BALANCE);

        //待结算 -> 待支付
        stateMachineBuilder
                .createExternalTransition()
                .from(StateEnum.STATE_WAIT_BALANCE)
                .on(EventEnum.EVENT_BALANCE)
                .when(ConditionEnum.IS_ABLE_BALANCE, stateEnumEventEnumIEventContext -> {
                    System.out.println("检查能否结算订单");
                    IEvent<EventEnum> event = stateEnumEventEnumIEventContext.getEvent();
                    Object[] payload = event.getPayload();
                    System.out.println("收到事件参数负载: " + Arrays.toString(payload));

                    return Boolean.TRUE;
                }).perform(ActionEnum.ACTION_BALANCE, args -> {
            System.out.println("执行订单结算动作");
            System.out.println("收到事件参数负载: " + Arrays.toString(args));

            return "动作结算订单执行成功";
        }).to(StateEnum.STATE_WAIT_PAY);

        //选择支付方式（待支付）
        stateMachineBuilder
                .createInternalTransition()
                .from(StateEnum.STATE_WAIT_PAY)
                .on(EventEnum.EVENT_CHOOSE_PAY_WAY)
                .when(ConditionEnum.IS_ABLE_CHOOSE_PAY_WAY, stateEnumEventEnumIEventContext -> {
                    System.out.println("检查能否选择支付方式");
                    IEvent<EventEnum> event = stateEnumEventEnumIEventContext.getEvent();
                    Object[] payload = event.getPayload();
                    System.out.println("收到事件参数负载: " + Arrays.toString(payload));

                    return Boolean.TRUE;
                }).perform(ActionEnum.ACTION_CHOOSE_PAY_WAY, args -> {
            System.out.println("执行选择支付方式动作");
            System.out.println("收到事件参数负载: " + Arrays.toString(args));

            return "动作选择支付方式执行成功";
        });

        //待支付 -> 待记账
        stateMachineBuilder
                .createExternalTransition()
                .from(StateEnum.STATE_WAIT_PAY)
                .on(EventEnum.EVENT_PAY)
                .when(ConditionEnum.IS_ABLE_PAY, stateEnumEventEnumIEventContext -> {
                    System.out.println("检查能否支付订单");
                    IEvent<EventEnum> event = stateEnumEventEnumIEventContext.getEvent();
                    Object[] payload = event.getPayload();
                    System.out.println("收到事件参数负载: " + Arrays.toString(payload));

                    return Boolean.TRUE;
                }).perform(ActionEnum.ACTION_PAY, args -> {
            System.out.println("执行订单结算动作");
            System.out.println("收到事件参数负载: " + Arrays.toString(args));

            return "动作结算订单执行成功";
        }).to(StateEnum.STATE_WAIT_BOOKING);

        //待记账 -> 完成
        stateMachineBuilder
                .createExternalTransition()
                .from(StateEnum.STATE_WAIT_BOOKING)
                .on(EventEnum.EVENT_BOOKING)
                .when(ConditionEnum.IS_ABLE_BOOKING, stateEnumEventEnumIEventContext -> {
                    System.out.println("检查能否记账");
                    IEvent<EventEnum> event = stateEnumEventEnumIEventContext.getEvent();
                    Object[] payload = event.getPayload();
                    System.out.println("收到事件参数负载: " + Arrays.toString(payload));

                    return Boolean.TRUE;
                }).perform(ActionEnum.ACTION_BOOKING, args -> {
            System.out.println("执行订单记账动作");
            System.out.println("收到事件参数负载: " + Arrays.toString(args));

            return "动作订单记账执行成功";
        }).to(StateEnum.STATE_FINISHED);

        //构建租金订单状态机
        stateMachine = stateMachineBuilder.build(StateMachineEnum.RENT_ORDER);
    }

    @Test
    public void init() {
        //01、初始化订单
//        EventContextImpl<StateEnum, EventEnum> eventContext = EventContextImpl.getInstance();
//        EventImpl<EventEnum> event = EventImpl.getInstance();
//        event.eventId(EventEnum.EVENT_INIT)
//                .payload("用户: 张三", "订单: xxxxxxx", "金额: 99", "商品: 租金");
//        eventContext.from(StateEnum.STATE_WAIT_INIT)
//                .on(event);
//        IStateContext<StateEnum, EventEnum> stateContext = stateMachine.fireEvent(eventContext);
//        IStateContext<StateEnum, EventEnum> stateContext = stateMachine.fireEvent(StateEnum.STATE_WAIT_INIT, EventEnum.EVENT_INIT, "用户: 张三", "订单: xxxxxxx", "金额: 99", "商品: 租金");
        IEventContextBuilder<StateEnum, EventEnum> eventContextBuilder = EventContextFactory.create();
        IEventBuilder<EventEnum> eventBuilder = EventFactory.create();
        IEvent<EventEnum> event = eventBuilder
                .payload("用户: 张三", "订单: xxxxxxx", "金额: 99", "商品: 租金")
                .id(EventEnum.EVENT_INIT)
                .build();
        IEventContext<StateEnum, EventEnum> eventContext = eventContextBuilder
                .from(StateEnum.STATE_WAIT_INIT)
                .on(event)
                .build();
        IStateContext<StateEnum, EventEnum> stateContext = stateMachine.fireEvent(eventContext);

        System.out.printf("执行后的状态[%s], 执行后的结果[%s]%n", stateContext.getStateId(), stateContext.getPayload());
    }

    @Test
    public void promo() {
        //02、营销
//        EventContextImpl<StateEnum, EventEnum> eventContext = EventContextImpl.getInstance();
//        EventImpl<EventEnum> event = EventImpl.getInstance();
//        event.eventId(EventEnum.EVENT_PROMO)
//                .payload("订单: xxxxxxx", "营销方案: 满100减50");
//        eventContext.from(StateEnum.STATE_WAIT_PROMO)
//                .on(event);
////        IStateContext<StateEnum, EventEnum> stateContext = stateMachine.fireEvent(eventContext);
//        IStateContext<StateEnum, EventEnum> stateContext = stateMachine.fireEvent(StateEnum.STATE_WAIT_PROMO, EventEnum.EVENT_PROMO, "订单: xxxxxxx", "营销方案: 满100减50");

        IEventContextBuilder<StateEnum, EventEnum> eventContextBuilder = EventContextFactory.create();
        IEventBuilder<EventEnum> eventBuilder = EventFactory.create();
        IEvent<EventEnum> event = eventBuilder.payload("订单: xxxxxxx", "营销方案: 满100减50")
                .id(EventEnum.EVENT_PROMO)
                .build();
        IEventContext<StateEnum, EventEnum> eventContext = eventContextBuilder.from(StateEnum.STATE_WAIT_PROMO)
                .on(event)
                .build();
        IStateContext<StateEnum, EventEnum> stateContext = stateMachine.fireEvent(eventContext);

        System.out.printf("执行后的状态[%s], 执行后的结果[%s]%n", stateContext.getStateId(), stateContext.getPayload());
    }

    @Test
    public void modifyPrice() {
        //03、修改金额
        EventContextImpl<StateEnum, EventEnum> eventContext = EventContextImpl.getInstance();
        EventImpl<EventEnum> event = EventImpl.getInstance();
        event.eventId(EventEnum.EVENT_MODIFY_PRICE)
                .payload("订单: xxxxxxx", "修改金额方案: 将金额修改为50");
        eventContext.from(StateEnum.STATE_WAIT_PROMO)
                .on(event);
//        IStateContext<StateEnum, EventEnum> stateContext = stateMachine.fireEvent(eventContext);
        IStateContext<StateEnum, EventEnum> stateContext = stateMachine.fireEvent(StateEnum.STATE_WAIT_PROMO, EventEnum.EVENT_MODIFY_PRICE, "订单: xxxxxxx", "修改金额方案: 将金额修改为50");

        System.out.printf("执行后的状态[%s], 执行后的结果[%s]%n", stateContext.getStateId(), stateContext.getPayload());
    }

    @Test
    public void balance() {
        //04、结算
        EventContextImpl<StateEnum, EventEnum> eventContext = EventContextImpl.getInstance();
        EventImpl<EventEnum> event = EventImpl.getInstance();
        event.eventId(EventEnum.EVENT_BALANCE)
                .payload("订单: xxxxxxx", "结算: 计算后的金额10");
        eventContext.from(StateEnum.STATE_WAIT_BALANCE)
                .on(event);
//        IStateContext<StateEnum, EventEnum> stateContext = stateMachine.fireEvent(eventContext);
        IStateContext<StateEnum, EventEnum> stateContext = stateMachine.fireEvent(StateEnum.STATE_WAIT_BALANCE, EventEnum.EVENT_BALANCE, "订单: xxxxxxx", "结算: 计算后的金额10");

        System.out.printf("执行后的状态[%s], 执行后的结果[%s]%n", stateContext.getStateId(), stateContext.getPayload());
    }

    @Test
    public void pay() {
        //05、支付
        EventContextImpl<StateEnum, EventEnum> eventContext = EventContextImpl.getInstance();
        EventImpl<EventEnum> event = EventImpl.getInstance();
        event.eventId(EventEnum.EVENT_PAY)
                .payload("订单: xxxxxxx", "支付金额: 10");
        eventContext.from(StateEnum.STATE_WAIT_PAY)
                .on(event);
//        IStateContext<StateEnum, EventEnum> stateContext = stateMachine.fireEvent(eventContext);
        IStateContext<StateEnum, EventEnum> stateContext = stateMachine.fireEvent(StateEnum.STATE_WAIT_PAY, EventEnum.EVENT_PAY, "订单: xxxxxxx", "支付金额: 10");

        System.out.printf("执行后的状态[%s], 执行后的结果[%s]%n", stateContext.getStateId(), stateContext.getPayload());
    }

    @Test
    public void booking() {
        //06、记账
        EventContextImpl<StateEnum, EventEnum> eventContext = EventContextImpl.getInstance();
        EventImpl<EventEnum> event = EventImpl.getInstance();
        event.eventId(EventEnum.EVENT_BOOKING)
                .payload("订单: xxxxxxx", "记账: 用户积分、信誉分改动");
        eventContext.from(StateEnum.STATE_WAIT_BOOKING)
                .on(event);
//        IStateContext<StateEnum, EventEnum> stateContext = stateMachine.fireEvent(eventContext);
        IStateContext<StateEnum, EventEnum> stateContext = stateMachine.fireEvent(StateEnum.STATE_WAIT_BOOKING, EventEnum.EVENT_BOOKING, "订单: xxxxxxx", "记账: 用户积分、信誉分改动");

        System.out.printf("执行后的状态[%s], 执行后的结果[%s]%n", stateContext.getStateId(), stateContext.getPayload());
    }

    @Test
    public void cancel() {
        //07、取消订单
        EventContextImpl<StateEnum, EventEnum> eventContext = EventContextImpl.getInstance();
        EventImpl<EventEnum> event = EventImpl.getInstance();
        event.eventId(EventEnum.EVENT_CANCEL)
                .payload("订单: xxxxxxx", "取消订单理由: 点错了");
        eventContext.from(StateEnum.STATE_WAIT_PAY)
                .on(event);
//        IStateContext<StateEnum, EventEnum> stateContext = stateMachine.fireEvent(eventContext);
        IStateContext<StateEnum, EventEnum> stateContext = stateMachine.fireEvent(StateEnum.STATE_WAIT_PAY, EventEnum.EVENT_CANCEL, "订单: xxxxxxx", "取消订单理由: 点错了");

        System.out.printf("执行后的状态[%s], 执行后的结果[%s]%n", stateContext.getStateId(), stateContext.getPayload());
    }

    @Test
    public void close() {
        //08、关闭订单
        EventContextImpl<StateEnum, EventEnum> eventContext = EventContextImpl.getInstance();
        EventImpl<EventEnum> event = EventImpl.getInstance();
        event.eventId(EventEnum.EVENT_CLOSE)
                .payload("订单: xxxxxxx", "关闭订单理由: 点错了");
        eventContext.from(StateEnum.STATE_WAIT_PAY)
                .on(event);
//        IStateContext<StateEnum, EventEnum> stateContext = stateMachine.fireEvent(eventContext);
        IStateContext<StateEnum, EventEnum> stateContext = stateMachine.fireEvent(StateEnum.STATE_WAIT_PAY, EventEnum.EVENT_CLOSE, "订单: xxxxxxx", "关闭订单理由: 点错了");

        System.out.printf("执行后的状态[%s], 执行后的结果[%s]%n", stateContext.getStateId(), stateContext.getPayload());
    }

    enum StateMachineEnum {
        /**
         * 租金订单
         */
        RENT_ORDER,
        ;
    }

    enum StateEnum {
        /**
         * 待初始化
         */
        STATE_WAIT_INIT,
        /**
         * 待营销
         */
        STATE_WAIT_PROMO,
        /**
         * 待结算
         */
        STATE_WAIT_BALANCE,
        /**
         * 待支付
         */
        STATE_WAIT_PAY,
        /**
         * 待记账
         */
        STATE_WAIT_BOOKING,
        /**
         * 已完成
         */
        STATE_FINISHED,
        /**
         * 已关闭
         */
        STATE_CLOSED,
        /**
         * 已取消
         */
        STATE_CANCELED,
        ;
    }

    enum EventEnum {
        /**
         * 初始化
         */
        EVENT_INIT,
        /**
         * 营销
         */
        EVENT_PROMO,
        /**
         * 结算
         */
        EVENT_BALANCE,
        /**
         * 修改订单金额
         */
        EVENT_MODIFY_PRICE,
        /**
         * 选择支付方式
         */
        EVENT_CHOOSE_PAY_WAY,
        /**
         * 支付
         */
        EVENT_PAY,
        /**
         * 记账
         */
        EVENT_BOOKING,
        /**
         * 关闭
         */
        EVENT_CLOSE,
        /**
         * 取消
         */
        EVENT_CANCEL,
        ;
    }

    enum ConditionEnum {
        /**
         * 能否初始化
         */
        IS_ABLE_INIT,
        /**
         * 能否营销
         */
        IS_ABLE_PROMO,
        /**
         * 能否结算
         */
        IS_ABLE_BALANCE,
        /**
         * 能否修改订单金额
         */
        IS_ABLE_MODIFY_PRICE,
        /**
         * 能否选择支付方式
         */
        IS_ABLE_CHOOSE_PAY_WAY,
        /**
         * 能否支付
         */
        IS_ABLE_PAY,
        /**
         * 能否记账
         */
        IS_ABLE_BOOKING,
        /**
         * 能否关闭
         */
        IS_ABLE_CLOSE,
        /**
         * 能否取消
         */
        IS_ABLE_CANCEL,
        ;
    }

    enum ActionEnum {
        /**
         * 初始化
         */
        ACTION_INIT,
        /**
         * 营销
         */
        ACTION_PROMO,
        /**
         * 结算
         */
        ACTION_BALANCE,
        /**
         * 能否修改订单金额
         */
        ACTION_MODIFY_PRICE,
        /**
         * 选择支付方式
         */
        ACTION_CHOOSE_PAY_WAY,
        /**
         * 支付
         */
        ACTION_PAY,
        /**
         * 记账
         */
        ACTION_BOOKING,
        /**
         * 关闭
         */
        ACTION_CLOSE,
        /**
         * 取消
         */
        ACTION_CANCEL,
        ;
    }

}