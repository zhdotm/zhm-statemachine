package io.github.zhdotm.statemachine.model.support;

import io.github.zhdotm.statemachine.model.domain.IEvent;
import io.github.zhdotm.statemachine.model.domain.IEventContext;
import io.github.zhdotm.statemachine.model.domain.IStateContext;
import io.github.zhdotm.statemachine.model.domain.IStateMachine;
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
                .when(ConditionEnum.IS_ABLE_CLOSE,
                        eventContext -> {
                            System.out.println("检查能否关闭订单");
                            IEvent<EventEnum> event = eventContext.getEvent();
                            Object[] payload = event.getPayload();
                            System.out.println("收到事件参数负载: " + Arrays.toString(payload));

                            return Boolean.TRUE;
                        })
                .perform(ActionEnum.ACTION_CLOSE,
                        args -> {
                            System.out.println("执行关闭订单动作");
                            System.out.println("收到事件参数负载: " + Arrays.toString(args));

                            return "动作关闭订单执行成功";
                        })
                .to(StateEnum.STATE_CLOSED)
                .build();

        //待初始化、待营销、待结算、待支付 -> 取消
        stateMachineBuilder
                .createExternalTransition()
                .from(StateEnum.STATE_WAIT_INIT,
                        StateEnum.STATE_WAIT_PROMO,
                        StateEnum.STATE_WAIT_BALANCE,
                        StateEnum.STATE_WAIT_PAY)
                .on(EventEnum.EVENT_CANCEL)
                .when(ConditionEnum.IS_ABLE_CANCEL,
                        eventContext -> {
                            System.out.println("检查能否取消订单");
                            IEvent<EventEnum> event = eventContext.getEvent();
                            Object[] payload = event.getPayload();
                            System.out.println("收到事件参数负载: " + Arrays.toString(payload));

                            return Boolean.TRUE;
                        }).perform(ActionEnum.ACTION_CANCEL,
                args -> {
                    System.out.println("执行取消订单动作");
                    System.out.println("收到事件参数负载: " + Arrays.toString(args));

                    return "动作取消执行成功";
                })
                .to(StateEnum.STATE_CANCELED)
                .build();

        //修改订单金额（待营销、待结算、待支付 ）
        stateMachineBuilder
                .createInternalTransition()
                .from(StateEnum.STATE_WAIT_PROMO,
                        StateEnum.STATE_WAIT_BALANCE,
                        StateEnum.STATE_WAIT_PAY)
                .on(EventEnum.EVENT_MODIFY_PRICE)
                .when(ConditionEnum.IS_ABLE_MODIFY_PRICE,
                        eventContext -> {
                            System.out.println("检查能否修改订单金额");
                            IEvent<EventEnum> event = eventContext.getEvent();
                            Object[] payload = event.getPayload();
                            System.out.println("收到事件参数负载: " + Arrays.toString(payload));

                            return Boolean.TRUE;
                        })
                .perform(ActionEnum.ACTION_MODIFY_PRICE,
                        args -> {
                            System.out.println("执行修改订单金额动作");
                            System.out.println("收到事件参数负载: " + Arrays.toString(args));

                            return "动作修改订单金额执行成功";
                        })
                .build();

        //待初始化 -> 待营销
        stateMachineBuilder
                .createExternalTransition()
                .from(StateEnum.STATE_WAIT_INIT)
                .on(EventEnum.EVENT_INIT)
                .when(ConditionEnum.IS_ABLE_INIT,
                        eventContext -> {
                            System.out.println("检查能否初始化订单");
                            IEvent<EventEnum> event = eventContext.getEvent();
                            Object[] payload = event.getPayload();
                            System.out.println("收到事件参数负载: " + Arrays.toString(payload));

                            return Boolean.TRUE;
                        }).perform(ActionEnum.ACTION_INIT,
                args -> {
                    System.out.println("执行初始化订单动作");
                    System.out.println("收到事件参数负载: " + Arrays.toString(args));

                    return "动作初始化执行成功";
                })
                .to(StateEnum.STATE_WAIT_PROMO)
                .build();

        //待营销 -> 待结算
        stateMachineBuilder
                .createExternalTransition()
                .from(StateEnum.STATE_WAIT_PROMO)
                .on(EventEnum.EVENT_PROMO)
                .when(ConditionEnum.IS_ABLE_PROMO,
                        eventContext -> {
                            System.out.println("检查能否给订单使用优惠券");
                            IEvent<EventEnum> event = eventContext.getEvent();
                            Object[] payload = event.getPayload();
                            System.out.println("收到事件参数负载: " + Arrays.toString(payload));

                            return Boolean.TRUE;
                        })
                .perform(ActionEnum.ACTION_PROMO,
                        args -> {
                            System.out.println("执行订单营销动作");
                            System.out.println("收到事件参数负载: " + Arrays.toString(args));

                            return "动作营销订单执行成功";
                        })
                .to(StateEnum.STATE_WAIT_BALANCE)
                .build();

        //待结算 -> 待支付
        stateMachineBuilder
                .createExternalTransition()
                .from(StateEnum.STATE_WAIT_BALANCE)
                .on(EventEnum.EVENT_BALANCE)
                .when(ConditionEnum.IS_ABLE_BALANCE,
                        eventContext -> {
                            System.out.println("检查能否结算订单");
                            IEvent<EventEnum> event = eventContext.getEvent();
                            Object[] payload = event.getPayload();
                            System.out.println("收到事件参数负载: " + Arrays.toString(payload));

                            return Boolean.TRUE;
                        })
                .perform(ActionEnum.ACTION_BALANCE,
                        args -> {
                            System.out.println("执行订单结算动作");
                            System.out.println("收到事件参数负载: " + Arrays.toString(args));

                            return "动作结算订单执行成功";
                        })
                .to(StateEnum.STATE_WAIT_PAY)
                .build();

        //选择支付方式（待支付）
        stateMachineBuilder
                .createInternalTransition()
                .from(StateEnum.STATE_WAIT_PAY)
                .on(EventEnum.EVENT_CHOOSE_PAY_WAY)
                .when(ConditionEnum.IS_ABLE_CHOOSE_PAY_WAY,
                        eventContext -> {
                            System.out.println("检查能否选择支付方式");
                            IEvent<EventEnum> event = eventContext.getEvent();
                            Object[] payload = event.getPayload();
                            System.out.println("收到事件参数负载: " + Arrays.toString(payload));

                            return Boolean.TRUE;
                        })
                .perform(ActionEnum.ACTION_CHOOSE_PAY_WAY,
                        args -> {
                            System.out.println("执行选择支付方式动作");
                            System.out.println("收到事件参数负载: " + Arrays.toString(args));

                            return "动作选择支付方式执行成功";
                        })
                .build();

        //待支付 -> 待记账
        stateMachineBuilder
                .createExternalTransition()
                .from(StateEnum.STATE_WAIT_PAY)
                .on(EventEnum.EVENT_PAY)
                .when(ConditionEnum.IS_ABLE_PAY,
                        eventContext -> {
                            System.out.println("检查能否支付订单");
                            IEvent<EventEnum> event = eventContext.getEvent();
                            Object[] payload = event.getPayload();
                            System.out.println("收到事件参数负载: " + Arrays.toString(payload));

                            return Boolean.TRUE;
                        })
                .perform(ActionEnum.ACTION_PAY,
                        args -> {
                            System.out.println("执行订单结算动作");
                            System.out.println("收到事件参数负载: " + Arrays.toString(args));

                            return "动作结算订单执行成功";
                        })
                .to(StateEnum.STATE_WAIT_BOOKING)
                .build();

        //待记账 -> 完成
        stateMachineBuilder
                .createExternalTransition()
                .from(StateEnum.STATE_WAIT_BOOKING)
                .on(EventEnum.EVENT_BOOKING)
                .when(ConditionEnum.IS_ABLE_BOOKING,
                        eventContext -> {
                            System.out.println("检查能否记账");
                            IEvent<EventEnum> event = eventContext.getEvent();
                            Object[] payload = event.getPayload();
                            System.out.println("收到事件参数负载: " + Arrays.toString(payload));

                            return Boolean.TRUE;
                        })
                .perform(ActionEnum.ACTION_BOOKING,
                        args -> {
                            System.out.println("执行订单记账动作");
                            System.out.println("收到事件参数负载: " + Arrays.toString(args));

                            return "动作订单记账执行成功";
                        })
                .to(StateEnum.STATE_FINISHED)
                .build();

        //构建租金订单状态机
        stateMachine = stateMachineBuilder.build(StateMachineEnum.RENT_ORDER);
    }

    /**
     * 01、初始化订单（事件上下文方式）
     */
    @Test
    public void init1() {
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

    /**
     * 01、初始化订单（原生方式）
     */
    @Test
    public void init2() {
        IStateContext<StateEnum, EventEnum> stateContext = stateMachine.fireEvent(StateEnum.STATE_WAIT_INIT, EventEnum.EVENT_INIT, "用户: 张三", "订单: xxxxxxx", "金额: 99", "商品: 租金");

        System.out.printf("执行后的状态[%s], 执行后的结果[%s]%n", stateContext.getStateId(), stateContext.getPayload());
    }

    /**
     * 02、营销（事件上下文方式）
     */
    @Test
    public void promo1() {
        IEventContextBuilder<StateEnum, EventEnum> eventContextBuilder = EventContextFactory.create();
        IEventBuilder<EventEnum> eventBuilder = EventFactory.create();
        IEvent<EventEnum> event = eventBuilder
                .payload("订单: xxxxxxx", "营销方案: 满100减50")
                .id(EventEnum.EVENT_PROMO)
                .build();
        IEventContext<StateEnum, EventEnum> eventContext = eventContextBuilder
                .from(StateEnum.STATE_WAIT_PROMO)
                .on(event)
                .build();

        IStateContext<StateEnum, EventEnum> stateContext = stateMachine.fireEvent(eventContext);

        System.out.printf("执行后的状态[%s], 执行后的结果[%s]%n", stateContext.getStateId(), stateContext.getPayload());
    }

    /**
     * 02、营销（原生方式）
     */
    @Test
    public void promo2() {
        IStateContext<StateEnum, EventEnum> stateContext = stateMachine.fireEvent(StateEnum.STATE_WAIT_PROMO, EventEnum.EVENT_PROMO, "订单: xxxxxxx", "营销方案: 满100减50");

        System.out.printf("执行后的状态[%s], 执行后的结果[%s]%n", stateContext.getStateId(), stateContext.getPayload());
    }

    /**
     * 03、修改金额（事件上下文方式）
     */
    @Test
    public void modifyPrice1() {
        IEventContextBuilder<StateEnum, EventEnum> eventContextBuilder = EventContextFactory.create();
        IEventBuilder<EventEnum> eventBuilder = EventFactory.create();
        IEvent<EventEnum> event = eventBuilder
                .payload("订单: xxxxxxx", "修改金额方案: 将金额修改为50")
                .id(EventEnum.EVENT_MODIFY_PRICE)
                .build();
        IEventContext<StateEnum, EventEnum> eventContext = eventContextBuilder
                .from(StateEnum.STATE_WAIT_PROMO)
                .on(event)
                .build();

        IStateContext<StateEnum, EventEnum> stateContext = stateMachine.fireEvent(eventContext);

        System.out.printf("执行后的状态[%s], 执行后的结果[%s]%n", stateContext.getStateId(), stateContext.getPayload());
    }

    /**
     * 03、修改金额（原生方式）
     */
    @Test
    public void modifyPrice2() {
        IStateContext<StateEnum, EventEnum> stateContext = stateMachine.fireEvent(StateEnum.STATE_WAIT_PROMO, EventEnum.EVENT_MODIFY_PRICE, "订单: xxxxxxx", "修改金额方案: 将金额修改为50");

        System.out.printf("执行后的状态[%s], 执行后的结果[%s]%n", stateContext.getStateId(), stateContext.getPayload());
    }

    /**
     * 04、结算（事件上下文方式）
     */
    @Test
    public void balance1() {
        IEventContextBuilder<StateEnum, EventEnum> eventContextBuilder = EventContextFactory.create();
        IEventBuilder<EventEnum> eventBuilder = EventFactory.create();
        IEvent<EventEnum> event = eventBuilder
                .payload("订单: xxxxxxx", "结算: 计算后的金额10")
                .id(EventEnum.EVENT_BALANCE)
                .build();
        IEventContext<StateEnum, EventEnum> eventContext = eventContextBuilder
                .from(StateEnum.STATE_WAIT_BALANCE)
                .on(event)
                .build();

        IStateContext<StateEnum, EventEnum> stateContext = stateMachine.fireEvent(eventContext);

        System.out.printf("执行后的状态[%s], 执行后的结果[%s]%n", stateContext.getStateId(), stateContext.getPayload());
    }

    /**
     * 04、结算（原生方式）
     */
    @Test
    public void balance2() {
        IStateContext<StateEnum, EventEnum> stateContext = stateMachine.fireEvent(StateEnum.STATE_WAIT_BALANCE, EventEnum.EVENT_BALANCE, "订单: xxxxxxx", "结算: 计算后的金额10");

        System.out.printf("执行后的状态[%s], 执行后的结果[%s]%n", stateContext.getStateId(), stateContext.getPayload());
    }

    /**
     * 05、支付（事件上下文方式）
     */
    @Test
    public void pay1() {
        IEventContextBuilder<StateEnum, EventEnum> eventContextBuilder = EventContextFactory.create();
        IEventBuilder<EventEnum> eventBuilder = EventFactory.create();
        IEvent<EventEnum> event = eventBuilder
                .payload("订单: xxxxxxx", "支付金额: 10")
                .id(EventEnum.EVENT_PAY)
                .build();
        IEventContext<StateEnum, EventEnum> eventContext = eventContextBuilder
                .from(StateEnum.STATE_WAIT_PAY)
                .on(event)
                .build();

        IStateContext<StateEnum, EventEnum> stateContext = stateMachine.fireEvent(eventContext);

        System.out.printf("执行后的状态[%s], 执行后的结果[%s]%n", stateContext.getStateId(), stateContext.getPayload());
    }

    /**
     * 05、支付（原生方式）
     */
    @Test
    public void pay2() {
        IStateContext<StateEnum, EventEnum> stateContext = stateMachine.fireEvent(StateEnum.STATE_WAIT_PAY, EventEnum.EVENT_PAY, "订单: xxxxxxx", "支付金额: 10");

        System.out.printf("执行后的状态[%s], 执行后的结果[%s]%n", stateContext.getStateId(), stateContext.getPayload());
    }

    /**
     * 06、记账（事件上下文方式）
     */
    @Test
    public void booking1() {
        IEventContextBuilder<StateEnum, EventEnum> eventContextBuilder = EventContextFactory.create();
        IEventBuilder<EventEnum> eventBuilder = EventFactory.create();
        IEvent<EventEnum> event = eventBuilder
                .payload("订单: xxxxxxx", "记账: 用户积分、信誉分改动")
                .id(EventEnum.EVENT_BOOKING)
                .build();
        IEventContext<StateEnum, EventEnum> eventContext = eventContextBuilder
                .from(StateEnum.STATE_WAIT_BOOKING)
                .on(event)
                .build();

        IStateContext<StateEnum, EventEnum> stateContext = stateMachine.fireEvent(eventContext);

        System.out.printf("执行后的状态[%s], 执行后的结果[%s]%n", stateContext.getStateId(), stateContext.getPayload());
    }

    /**
     * 06、记账（原生方式）
     */
    @Test
    public void booking2() {
        IStateContext<StateEnum, EventEnum> stateContext = stateMachine.fireEvent(StateEnum.STATE_WAIT_BOOKING, EventEnum.EVENT_BOOKING, "订单: xxxxxxx", "记账: 用户积分、信誉分改动");

        System.out.printf("执行后的状态[%s], 执行后的结果[%s]%n", stateContext.getStateId(), stateContext.getPayload());
    }

    /**
     * 07、取消订单（事件上下文方式）
     */
    @Test
    public void cancel1() {
        IEventContextBuilder<StateEnum, EventEnum> eventContextBuilder = EventContextFactory.create();
        IEventBuilder<EventEnum> eventBuilder = EventFactory.create();
        IEvent<EventEnum> event = eventBuilder
                .payload("订单: xxxxxxx", "取消订单理由: 点错了")
                .id(EventEnum.EVENT_CANCEL)
                .build();
        IEventContext<StateEnum, EventEnum> eventContext = eventContextBuilder
                .from(StateEnum.STATE_WAIT_PAY)
                .on(event)
                .build();

        IStateContext<StateEnum, EventEnum> stateContext = stateMachine.fireEvent(eventContext);

        System.out.printf("执行后的状态[%s], 执行后的结果[%s]%n", stateContext.getStateId(), stateContext.getPayload());
    }

    /**
     * 07、取消订单（原生方式）
     */
    @Test
    public void cancel2() {
        IStateContext<StateEnum, EventEnum> stateContext = stateMachine.fireEvent(StateEnum.STATE_WAIT_PAY, EventEnum.EVENT_CANCEL, "订单: xxxxxxx", "取消订单理由: 点错了");

        System.out.printf("执行后的状态[%s], 执行后的结果[%s]%n", stateContext.getStateId(), stateContext.getPayload());
    }

    /**
     * 08、关闭订单（事件上下文方式）
     */
    @Test
    public void close1() {
        IEventContextBuilder<StateEnum, EventEnum> eventContextBuilder = EventContextFactory.create();
        IEventBuilder<EventEnum> eventBuilder = EventFactory.create();
        IEvent<EventEnum> event = eventBuilder
                .payload("订单: xxxxxxx", "关闭订单理由: 点错了")
                .id(EventEnum.EVENT_CLOSE)
                .build();
        IEventContext<StateEnum, EventEnum> eventContext = eventContextBuilder
                .from(StateEnum.STATE_WAIT_PAY)
                .on(event)
                .build();

        IStateContext<StateEnum, EventEnum> stateContext = stateMachine.fireEvent(eventContext);

        System.out.printf("执行后的状态[%s], 执行后的结果[%s]%n", stateContext.getStateId(), stateContext.getPayload());
    }

    /**
     * 08、关闭订单（原生方式）
     */
    @Test
    public void close2() {
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