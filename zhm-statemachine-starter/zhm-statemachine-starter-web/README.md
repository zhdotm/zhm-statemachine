# Spring框架下使用

## 1、添加依赖

```xml

<dependency>
    <groupId>io.github.zhdotm</groupId>
    <artifactId>zhm-statemachine-starter-web</artifactId>
    <version>1.1.5</version>
</dependency>
```

## 2、修改配置

enable：是否开启状态机自动组装

print：组装状态机成功后是否打印状态机内部结构

```yaml
zhm:
  statemachine:
    enable: true
    print: true
```

## 3、定义状态机组件

如果一个实现了ITransitionAdapter接口的类被@StateMachineComponent注解，且用@StateMachineCondition、@StateMachineAction指定了该类上的方法为条件判断方法和动作方法，那么这个类可被视为一个状态机组件。多个相同stateMachineId的状态机组件构成一个状态机。

### 3.1、注意

- @StateMachineCondition、@StateMachineAction在@StateMachineComponent指定的类上有且仅能出现一次
- @StateMachineCondition、@StateMachineAction若不指定ID则采用方法名作为ID
- @StateMachineCondition、@StateMachineAction入参必须相同
- @StateMachineCondition指定方法的返回值必须是boolean类型
- @StateMachineCondition、@StateMachineAction指定的方法体内可以调用getCurrentState()
  方法获取当前状态，条件方法获取的当前状态是开始状态，动作方法获取的当前状态是流转后的状态（当状态机组件为内部流转组件时，开始状态与流转后的状态相同）。

```java
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
```

```java
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

    @StateMachineAction(acitonId = "ACTION_PROMO")
    public String execute(String orderId, String couponId) {
        System.out.println("执行营销订单动作");
        System.out.printf("订单[%s]使用优惠券[%s]%n", orderId, couponId);

        return "执行营销订单" + orderId + "动作结束";
    }

}
```

```java
/**
 * 结算
 *
 * @author zhihao.mao
 */

@Component
@StateMachineComponent(
        stateMachineId = "RENT_ORDER",
        type = TransitionTypeEnum.EXTERNAL,
        from = {"STATE_WAIT_BALANCE"},
        on = "EVENT_BALANCE",
        to = "STATE_WAIT_PAY"
)
public class OrderBalanceService implements ITransitionAdapter {

    @StateMachineCondition(conditionId = "IS_ABLE_BALANCE")
    public Boolean check(String orderId) {
        System.out.println("检查能否结算订单");

        return Boolean.TRUE;
    }

    @StateMachineAction(acitonId = "ACTION_BALANCE")
    public String execute(String orderId) {
        System.out.println("执行订单结算动作");
        System.out.println("订单" + orderId + "结算后的金额为50");

        return "执行结束";
    }

}
```

```java
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
        System.out.println("检查能否关闭订单");

        return Boolean.TRUE;
    }

    @StateMachineAction(acitonId = "ACTION_PAY")
    public String execute(String arg) {
        System.out.println("执行关闭订单动作");

        return "执行结束";
    }

}
```

```java
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

    @StateMachineAction(acitonId = "ACTION_BOOKING")
    public String execute(String orderId) {
        System.out.println("执行订单记账动作");
        System.out.println("订单" + orderId + "执行订单记账动作");

        return "执行结算订单" + orderId + "结束";
    }

}
```

```java
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
```

```java
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

    @StateMachineAction(acitonId = "ACTION_MODIFY_PRICE")
    public String execute(String orderId, Long price) {
        System.out.println("执行修改订单金额动作");
        System.out.println("修改订单金额: 将" + orderId + "的订单金额修改为" + price);

        return "执行修改订单" + orderId + "金额为" + price + "结束";
    }

}
```

```java
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
```

```java
/**
 * 关闭订单
 *
 * @author zhihao.mao
 */

@Component
@StateMachineComponent(
        stateMachineId = "RENT_ORDER",
        type = TransitionTypeEnum.EXTERNAL,
        from = {"STATE_WAIT_INIT", "STATE_WAIT_PROMO", "STATE_WAIT_BALANCE", "STATE_WAIT_PAY"},
        on = "EVENT_CLOSE",
        to = "STATE_CLOSED"
)
public class OrderCloseService implements ITransitionAdapter {

    @StateMachineCondition(conditionId = "IS_ABLE_CLOSE")
    public Boolean check(String orderId) {
        System.out.println("检查能否关闭订单");

        return Boolean.TRUE;
    }

    @StateMachineAction(acitonId = "ACTION_CLOSE")
    public String execute(String orderId) {
        System.out.println("执行关闭订单动作");
        System.out.println("关闭订单: " + orderId);

        return "关闭订单" + orderId + "执行结束";
    }

}
```

## 4、发送事件

```java

    /**
     * 关闭订单（事件上下文方式）
     */
    @Test
    public void close1() {

        IEventContextBuilder<String, String> eventContextBuilder = EventContextFactory.create();
        IEventBuilder<String> eventBuilder = EventFactory.create();
        IEvent<String> event = eventBuilder
                .payload("orderId:123456789")
                .id("EVENT_CLOSE")
                .build();
        IEventContext<String, String> eventContext = eventContextBuilder
                .from("STATE_WAIT_PROMO")
                .on(event)
                .build();

        IStateContext<String, String> stateContext = StateMachineSupport.fireEvent("RENT_ORDER", eventContext);

        System.out.println(JSON.toJSONString(stateContext));
    }

    /**
     * 关闭订单（原生方式）
     */
    @Test
    public void close2() {
        IStateContext<String, String> stateContext = StateMachineSupport
                .fireEvent("RENT_ORDER", "STATE_WAIT_PROMO", "EVENT_CLOSE", "orderId:123456789");

        System.out.println(JSON.toJSONString(stateContext));
    }


    /**
     * 取消订单（事件上下文方式）
     */
    @Test
    public void cancel1() {
        IEventContextBuilder<String, String> eventContextBuilder = EventContextFactory.create();
        IEventBuilder<String> eventBuilder = EventFactory.create();
        IEvent<String> event = eventBuilder
                .payload("orderId:123456789")
                .id("EVENT_CLOSE")
                .build();
        IEventContext<String, String> eventContext = eventContextBuilder
                .from("STATE_WAIT_PROMO")
                .on(event)
                .build();
        IStateContext<String, String> stateContext = StateMachineSupport
                .fireEvent("RENT_ORDER", eventContext);

        System.out.println(JSON.toJSONString(stateContext));
    }

    /**
     * 取消订单（原生方式）
     */
    @Test
    public void cancel2() {
        IStateContext<String, String> stateContext = StateMachineSupport
                .fireEvent("RENT_ORDER", "STATE_WAIT_PAY", "EVENT_CANCEL", "orderId:123456789");

        System.out.println(JSON.toJSONString(stateContext));
    }

    /**
     * 初始化订单（事件上下文方式）
     */
    @Test
    public void init1() {
        IEventContextBuilder<String, String> eventContextBuilder = EventContextFactory.create();
        IEventBuilder<String> eventBuilder = EventFactory.create();
        IEvent<String> event = eventBuilder
                .payload("orderId:111111", "buyerId:2222222", "commodityId:333333", 5, 500L)
                .id("EVENT_INIT")
                .build();
        IEventContext<String, String> eventContext = eventContextBuilder
                .from("STATE_WAIT_INIT")
                .on(event)
                .build();

        IStateContext<String, String> stateContext = StateMachineSupport
                .fireEvent("RENT_ORDER", eventContext);

        System.out.println(JSON.toJSONString(stateContext));
    }

    /**
     * 初始化订单（原生方式）
     */
    @Test
    public void init2() {
        IStateContext<String, String> stateContext = StateMachineSupport
                .fireEvent("RENT_ORDER", "STATE_WAIT_INIT", "EVENT_INIT", "orderId:111111", "buyerId:2222222", "commodityId:333333", 5, 500L);

        System.out.println(JSON.toJSONString(stateContext));
    }

    /**
     * 营销订单（事件上下文方式）
     */
    @Test
    public void promo1() {
        IEventContextBuilder<String, String> eventContextBuilder = EventContextFactory.create();
        IEventBuilder<String> eventBuilder = EventFactory.create();
        IEvent<String> event = eventBuilder
                .payload("orderId:111111", "couponId:222222")
                .id("EVENT_PROMO")
                .build();
        IEventContext<String, String> eventContext = eventContextBuilder
                .from("STATE_WAIT_PROMO")
                .on(event)
                .build();

        IStateContext<String, String> stateContext = StateMachineSupport
                .fireEvent("RENT_ORDER", eventContext);

        System.out.println(JSON.toJSONString(stateContext));
    }

    /**
     * 营销订单（原生方式）
     */
    @Test
    public void promo2() {
        IStateContext<String, String> stateContext = StateMachineSupport
                .fireEvent("RENT_ORDER", "STATE_WAIT_PROMO", "EVENT_PROMO", "orderId:111111", "couponId:222222");

        System.out.println(JSON.toJSONString(stateContext));
    }

    /**
     * 修改订单金额（事件上下文方式）
     */
    @Test
    public void modifyPrice1() {
        IEventContextBuilder<String, String> eventContextBuilder = EventContextFactory.create();
        IEventBuilder<String> eventBuilder = EventFactory.create();
        IEvent<String> event = eventBuilder
                .payload("orderId:111111", 200L)
                .id("EVENT_MODIFY_PRICE")
                .build();
        IEventContext<String, String> eventContext = eventContextBuilder
                .from("STATE_WAIT_PAY")
                .on(event)
                .build();

        IStateContext<String, String> stateContext = StateMachineSupport
                .fireEvent("RENT_ORDER", eventContext);

        System.out.println(JSON.toJSONString(stateContext));
    }

    /**
     * 修改订单金额（原生方式）
     */
    @Test
    public void modifyPrice2() {
        IStateContext<String, String> stateContext = StateMachineSupport
                .fireEvent("RENT_ORDER", "STATE_WAIT_PAY", "EVENT_MODIFY_PRICE", "orderId:111111", 200L);

        System.out.println(JSON.toJSONString(stateContext));
    }

    /**
     * 结算订单（事件上下文方式）
     */
    @Test
    public void balance1() {
        IEventContextBuilder<String, String> eventContextBuilder = EventContextFactory.create();
        IEventBuilder<String> eventBuilder = EventFactory.create();
        IEvent<String> event = eventBuilder
                .payload("orderId:111111")
                .id("EVENT_BALANCE")
                .build();
        IEventContext<String, String> eventContext = eventContextBuilder
                .from("STATE_WAIT_BALANCE")
                .on(event)
                .build();

        IStateContext<String, String> stateContext = StateMachineSupport
                .fireEvent("RENT_ORDER", eventContext);

        System.out.println(JSON.toJSONString(stateContext));
    }

    /**
     * 结算订单（原生方式）
     */
    @Test
    public void balance2() {
        IStateContext<String, String> stateContext = StateMachineSupport
                .fireEvent("RENT_ORDER", "STATE_WAIT_BALANCE", "EVENT_BALANCE", "orderId:111111");

        System.out.println(JSON.toJSONString(stateContext));
    }

    /**
     * 支付订单（事件上下文方式）
     */
    @Test
    public void pay1() {
        IEventContextBuilder<String, String> eventContextBuilder = EventContextFactory.create();
        IEventBuilder<String> eventBuilder = EventFactory.create();
        IEvent<String> event = eventBuilder
                .payload("orderId:111111")
                .id("EVENT_PAY")
                .build();
        IEventContext<String, String> eventContext = eventContextBuilder
                .from("STATE_WAIT_PAY")
                .on(event)
                .build();

        IStateContext<String, String> stateContext = StateMachineSupport
                .fireEvent("RENT_ORDER", eventContext);

        System.out.println(JSON.toJSONString(stateContext));
    }

    /**
     * 支付订单（原生方式）
     */
    @Test
    public void pay2() {
        IStateContext<String, String> stateContext = StateMachineSupport
                .fireEvent("RENT_ORDER", "STATE_WAIT_PAY", "EVENT_PAY", "orderId:111111");

        System.out.println(JSON.toJSONString(stateContext));
    }

    /**
     * 记账订单（事件上下文方式）
     */
    @Test
    public void booking1() {
        IEventContextBuilder<String, String> eventContextBuilder = EventContextFactory.create();
        IEventBuilder<String> eventBuilder = EventFactory.create();
        IEvent<String> event = eventBuilder
                .payload("orderId:111111")
                .id("EVENT_BOOKING")
                .build();
        IEventContext<String, String> eventContext = eventContextBuilder
                .from("STATE_WAIT_BOOKING")
                .on(event)
                .build();

        IStateContext<String, String> stateContext = StateMachineSupport
                .fireEvent("RENT_ORDER", eventContext);

        System.out.println(JSON.toJSONString(stateContext));
    }

    /**
     * 记账订单（原生方式）
     */
    @Test
    public void booking2() {
        IStateContext<String, String> stateContext = StateMachineSupport
                .fireEvent("RENT_ORDER", "STATE_WAIT_BOOKING", "EVENT_BOOKING", "orderId:111111");

        System.out.println(JSON.toJSONString(stateContext));
    }

```
