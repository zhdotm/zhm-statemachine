# zhm-statemachine

## 背景

公司内订单系统、合同系统等相关业务其内部业务逻辑复杂，难以光从代码中清晰得到相关业务状态流转。

参考：https://blog.csdn.net/significantfrank/article/details/104996419

### 使用场景

适合在业务流程较长场景较为复杂的需求中使用。

### 与cola-statemachine的区别

zhm-statemachine灵感来源于https://github.com/alibaba/COLA/tree/master/cola-components/cola-component-statemachine ，并在其设计思想的前提下，做了部分改动。

#### 1、统一外部流转、内部流转、批量流转的语法

##### cola-statemachine语法

构建外部转换

```java
builder.externalTransition()
        .from(States.STATE1)
        .to(States.STATE2)
        .on(Events.EVENT1)
        .when(checkCondition())
        .perform(doAction());
```

构建内部流转

```java
builder.internalTransition()
        .within(States.STATE1)
        .on(Events.INTERNAL_EVENT)
        .when(checkCondition())
        .perform(doAction());
```

构建批量流转

```java
builder.externalTransitions()
        .fromAmong(States.STATE1,States.STATE2,States.STATE3)
        .to(States.STATE4)
        .on(Events.EVENT1)
        .when(checkCondition())
        .perform(doAction());
```

##### zhm-statemachine语法

统一流转构建

创建外部流转

```java
//待结算 -> 待支付
stateMachineBuilder
        .createExternalTransition()
        .from(StateEnum.STATE_WAIT_BALANCE)
        .on(EventEnum.EVENT_BALANCE)
        .when(ConditionEnum.IS_ABLE_BALANCE,stateEnumEventEnumIEventContext->{
        System.out.println("检查能否结算订单");
        IEvent<EventEnum> event=stateEnumEventEnumIEventContext.getEvent();
        Object[]payload=event.getPayload();
        System.out.println("收到事件参数负载: "+Arrays.toString(payload));

        return Boolean.TRUE;
        }).perform(ActionEnum.ACTION_BALANCE,args->{
        System.out.println("执行订单结算动作");
        System.out.println("收到事件参数负载: "+Arrays.toString(args));

        return"动作结算订单执行成功";
        }).to(StateEnum.STATE_WAIT_PAY);
```

创建内部流转

```java
//选择支付方式（待支付）
stateMachineBuilder
        .createInternalTransition()
        .from(StateEnum.STATE_WAIT_PAY)
        .on(EventEnum.EVENT_CHOOSE_PAY_WAY)
        .when(ConditionEnum.IS_ABLE_CHOOSE_PAY_WAY,stateEnumEventEnumIEventContext->{
        System.out.println("检查能否选择支付方式");
        IEvent<EventEnum> event=stateEnumEventEnumIEventContext.getEvent();
        Object[]payload=event.getPayload();
        System.out.println("收到事件参数负载: "+Arrays.toString(payload));

        return Boolean.TRUE;
        }).perform(ActionEnum.ACTION_CHOOSE_PAY_WAY,args->{
        System.out.println("执行选择支付方式动作");
        System.out.println("收到事件参数负载: "+Arrays.toString(args));

        return"动作选择支付方式执行成功";
        });
```

创建批量流转

```java
//修改订单金额（待营销、待结算、待支付 ）
stateMachineBuilder
        .createInternalTransition()
        .from(StateEnum.STATE_WAIT_PROMO,
        StateEnum.STATE_WAIT_BALANCE,
        StateEnum.STATE_WAIT_PAY)
        .on(EventEnum.EVENT_MODIFY_PRICE)
        .when(ConditionEnum.IS_ABLE_MODIFY_PRICE,stateEnumEventEnumIEventContext->{
        System.out.println("检查能否修改订单金额");
        IEvent<EventEnum> event=stateEnumEventEnumIEventContext.getEvent();
        Object[]payload=event.getPayload();
        System.out.println("收到事件参数负载: "+Arrays.toString(payload));

        return Boolean.TRUE;
        }).perform(ActionEnum.ACTION_MODIFY_PRICE,args->{
        System.out.println("执行修改订单金额动作");
        System.out.println("收到事件参数负载: "+Arrays.toString(args));

        return"动作修改订单金额执行成功";
        });
```

#### 2、引入事件上下文概念

##### cola-statemachine语法

cola-statemachine的模型中并未设计事件上下文概念

```java
States target=stateMachine.fireEvent(States.STATE1,Events.EVENT1,new Context());
```

##### zhm-statemachine语法

引入事件上下文概念，事件负载采用不定参数，事件上下文绑定状态、事件、以及负载。

发送事件可以采用发送事件上下文语法，或类cola-statemachine的发送事件语法。

```java
    //08、关闭订单
        EventContextImpl<StateEnum, EventEnum> eventContext=EventContextImpl.getInstance();
        EventImpl<EventEnum> event=EventImpl.getInstance();
        event.eventId(EventEnum.EVENT_CLOSE)
        .payload("订单: xxxxxxx","关闭订单理由: 点错了");
        eventContext.stateId(StateEnum.STATE_WAIT_PAY)
        .event(event);
//        IStateContext<StateEnum, EventEnum> stateContext = stateMachine.fireEvent(eventContext);
        IStateContext<StateEnum, EventEnum> stateContext=stateMachine.fireEvent(StateEnum.STATE_WAIT_PAY,EventEnum.EVENT_CLOSE,"订单: xxxxxxx","关闭订单理由: 点错了");
```

#### 3、引入状态上下文概念

##### cola-statemachine语法

cola-statemachine的模型中并未设计状态上下文概念，触发完事件后，仅仅返还流转后的状态，并未携带流转后的信息。

```java
States target=stateMachine.fireEvent(States.STATE1,Events.EVENT1,new Context());
        Assert.assertEquals(States.STATE2,target);
```

##### zhm-statemachine语法

触发完事件后，返回状态上下文，状态上下文中携带流转后的状态、状态负载，以及触发该流转的事件上下文。

```java
IStateContext<StateEnum, EventEnum> stateContext=stateMachine.fireEvent(StateEnum.STATE_WAIT_PAY,EventEnum.EVENT_CLOSE,"订单: xxxxxxx","关闭订单理由: 点错了");

        System.out.printf("执行后的状态[%s], 执行后的结果[%s]%n",stateContext.getStateId(),stateContext.getPayload());
```

#### 4、声明式定义状态机

##### cola-statemachine语法

并未提供

##### zhm-statemachine语法

引入@StateMachineComponent、@StateMachineAction、@StateMachineCondition注解用于声明一个状态机组件。

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

    @StateMachineAction(actionId = "ACTION_BALANCE")
    public String execute(String orderId) {
        System.out.println("执行订单结算动作");
        System.out.println("订单" + orderId + "结算后的金额为50");

        return "执行结束";
    }

}
```

## 介绍

### 核心概念

![image-20221120215038268](https://raw.githubusercontent.com/zhdotm/picture-storage/main/image-20221120215038268.png)

#### 1、State

状态

#### 2、Event

事件，状态由事件触发，引起变化

#### 3、Transition

流转，表示从一个状态到另一个状态

#### 4、External Transition

外部流转，两个不同状态之间的流转

#### 5、Internal Transition

内部流转，同一个状态之间的流转

#### 6、Condition

条件，表示是否允许到达某个状态

#### 7、Action

动作，到达某个状态之后，可以做什么

#### 8、StateMachine

状态机

### 使用

以订单系统为例

#### 状态流转图

![订单状态流转图.drawio](https://raw.githubusercontent.com/zhdotm/picture-storage/main/%E8%AE%A2%E5%8D%95%E7%8A%B6%E6%80%81%E6%B5%81%E8%BD%AC%E5%9B%BE.drawio.png)

#### 原生场景下使用

##### 1、添加依赖

```xml

<dependency>
    <groupId>io.github.zhdotm</groupId>
    <artifactId>zhm-statemachine-model</artifactId>
    <version>1.1.2</version>
</dependency>
```

##### 2、定义状态机ID

```java
enum StateMachineEnum {
    /**
     * 租金订单
     */
    RENT_ORDER,
    ;
}
```

##### 3、定义状态ID

```java
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
```

##### 4、定义事件ID

```java
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
```

##### 5、定义条件ID

```java
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
```

##### 6、定义动作ID

```java
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
```

##### 7、构建状态机

```java
public void build(){
        StateMachineBuilder<StateMachineEnum, StateEnum, EventEnum, ConditionEnum, ActionEnum> stateMachineBuilder=StateMachineFactory.create();

        //待初始化、待营销、待结算、待支付 -> 关闭
        stateMachineBuilder
        .createExternalTransition()
        .from(StateEnum.STATE_WAIT_INIT,
        StateEnum.STATE_WAIT_PROMO,
        StateEnum.STATE_WAIT_BALANCE,
        StateEnum.STATE_WAIT_PAY)
        .on(EventEnum.EVENT_CLOSE)
        .when(ConditionEnum.IS_ABLE_CLOSE,stateEnumEventEnumIEventContext->{
        System.out.println("检查能否关闭订单");
        IEvent<EventEnum> event=stateEnumEventEnumIEventContext.getEvent();
        Object[]payload=event.getPayload();
        System.out.println("收到事件参数负载: "+Arrays.toString(payload));

        return Boolean.TRUE;
        }).perform(ActionEnum.ACTION_CLOSE,args->{
        System.out.println("执行关闭订单动作");
        System.out.println("收到事件参数负载: "+Arrays.toString(args));

        return"动作关闭订单执行成功";
        }).to(StateEnum.STATE_CLOSED);

        //待初始化、待营销、待结算、待支付 -> 取消
        stateMachineBuilder
        .createExternalTransition()
        .from(StateEnum.STATE_WAIT_INIT,
        StateEnum.STATE_WAIT_PROMO,
        StateEnum.STATE_WAIT_BALANCE,
        StateEnum.STATE_WAIT_PAY)
        .on(EventEnum.EVENT_CANCEL)
        .when(ConditionEnum.IS_ABLE_CANCEL,stateEnumEventEnumIEventContext->{
        System.out.println("检查能否取消订单");
        IEvent<EventEnum> event=stateEnumEventEnumIEventContext.getEvent();
        Object[]payload=event.getPayload();
        System.out.println("收到事件参数负载: "+Arrays.toString(payload));

        return Boolean.TRUE;
        }).perform(ActionEnum.ACTION_CANCEL,args->{
        System.out.println("执行取消订单动作");
        System.out.println("收到事件参数负载: "+Arrays.toString(args));

        return"动作取消执行成功";
        }).to(StateEnum.STATE_CANCELED);

        //修改订单金额（待营销、待结算、待支付 ）
        stateMachineBuilder
        .createInternalTransition()
        .from(StateEnum.STATE_WAIT_PROMO,
        StateEnum.STATE_WAIT_BALANCE,
        StateEnum.STATE_WAIT_PAY)
        .on(EventEnum.EVENT_MODIFY_PRICE)
        .when(ConditionEnum.IS_ABLE_MODIFY_PRICE,stateEnumEventEnumIEventContext->{
        System.out.println("检查能否修改订单金额");
        IEvent<EventEnum> event=stateEnumEventEnumIEventContext.getEvent();
        Object[]payload=event.getPayload();
        System.out.println("收到事件参数负载: "+Arrays.toString(payload));

        return Boolean.TRUE;
        }).perform(ActionEnum.ACTION_MODIFY_PRICE,args->{
        System.out.println("执行修改订单金额动作");
        System.out.println("收到事件参数负载: "+Arrays.toString(args));

        return"动作修改订单金额执行成功";
        });

        //待初始化 -> 待营销
        stateMachineBuilder
        .createExternalTransition()
        .from(StateEnum.STATE_WAIT_INIT)
        .on(EventEnum.EVENT_INIT)
        .when(ConditionEnum.IS_ABLE_INIT,stateEnumEventEnumIEventContext->{
        System.out.println("检查能否初始化订单");
        IEvent<EventEnum> event=stateEnumEventEnumIEventContext.getEvent();
        Object[]payload=event.getPayload();
        System.out.println("收到事件参数负载: "+Arrays.toString(payload));

        return Boolean.TRUE;
        }).perform(ActionEnum.ACTION_INIT,args->{
        System.out.println("执行初始化订单动作");
        System.out.println("收到事件参数负载: "+Arrays.toString(args));

        return"动作初始化执行成功";
        }).to(StateEnum.STATE_WAIT_PROMO);

        //待营销 -> 待结算
        stateMachineBuilder
        .createExternalTransition()
        .from(StateEnum.STATE_WAIT_PROMO)
        .on(EventEnum.EVENT_PROMO)
        .when(ConditionEnum.IS_ABLE_PROMO,stateEnumEventEnumIEventContext->{
        System.out.println("检查能否给订单使用优惠券");
        IEvent<EventEnum> event=stateEnumEventEnumIEventContext.getEvent();
        Object[]payload=event.getPayload();
        System.out.println("收到事件参数负载: "+Arrays.toString(payload));

        return Boolean.TRUE;
        }).perform(ActionEnum.ACTION_PROMO,args->{
        System.out.println("执行订单营销动作");
        System.out.println("收到事件参数负载: "+Arrays.toString(args));

        return"动作营销订单执行成功";
        }).to(StateEnum.STATE_WAIT_BALANCE);

        //待结算 -> 待支付
        stateMachineBuilder
        .createExternalTransition()
        .from(StateEnum.STATE_WAIT_BALANCE)
        .on(EventEnum.EVENT_BALANCE)
        .when(ConditionEnum.IS_ABLE_BALANCE,stateEnumEventEnumIEventContext->{
        System.out.println("检查能否结算订单");
        IEvent<EventEnum> event=stateEnumEventEnumIEventContext.getEvent();
        Object[]payload=event.getPayload();
        System.out.println("收到事件参数负载: "+Arrays.toString(payload));

        return Boolean.TRUE;
        }).perform(ActionEnum.ACTION_BALANCE,args->{
        System.out.println("执行订单结算动作");
        System.out.println("收到事件参数负载: "+Arrays.toString(args));

        return"动作结算订单执行成功";
        }).to(StateEnum.STATE_WAIT_PAY);

        //选择支付方式（待支付）
        stateMachineBuilder
        .createInternalTransition()
        .from(StateEnum.STATE_WAIT_PAY)
        .on(EventEnum.EVENT_CHOOSE_PAY_WAY)
        .when(ConditionEnum.IS_ABLE_CHOOSE_PAY_WAY,stateEnumEventEnumIEventContext->{
        System.out.println("检查能否选择支付方式");
        IEvent<EventEnum> event=stateEnumEventEnumIEventContext.getEvent();
        Object[]payload=event.getPayload();
        System.out.println("收到事件参数负载: "+Arrays.toString(payload));

        return Boolean.TRUE;
        }).perform(ActionEnum.ACTION_CHOOSE_PAY_WAY,args->{
        System.out.println("执行选择支付方式动作");
        System.out.println("收到事件参数负载: "+Arrays.toString(args));

        return"动作选择支付方式执行成功";
        });

        //待支付 -> 待记账
        stateMachineBuilder
        .createExternalTransition()
        .from(StateEnum.STATE_WAIT_PAY)
        .on(EventEnum.EVENT_PAY)
        .when(ConditionEnum.IS_ABLE_PAY,stateEnumEventEnumIEventContext->{
        System.out.println("检查能否支付订单");
        IEvent<EventEnum> event=stateEnumEventEnumIEventContext.getEvent();
        Object[]payload=event.getPayload();
        System.out.println("收到事件参数负载: "+Arrays.toString(payload));

        return Boolean.TRUE;
        }).perform(ActionEnum.ACTION_PAY,args->{
        System.out.println("执行订单结算动作");
        System.out.println("收到事件参数负载: "+Arrays.toString(args));

        return"动作结算订单执行成功";
        }).to(StateEnum.STATE_WAIT_BOOKING);

        //待记账 -> 完成
        stateMachineBuilder
        .createExternalTransition()
        .from(StateEnum.STATE_WAIT_BOOKING)
        .on(EventEnum.EVENT_BOOKING)
        .when(ConditionEnum.IS_ABLE_BOOKING,stateEnumEventEnumIEventContext->{
        System.out.println("检查能否记账");
        IEvent<EventEnum> event=stateEnumEventEnumIEventContext.getEvent();
        Object[]payload=event.getPayload();
        System.out.println("收到事件参数负载: "+Arrays.toString(payload));

        return Boolean.TRUE;
        }).perform(ActionEnum.ACTION_BOOKING,args->{
        System.out.println("执行订单记账动作");
        System.out.println("收到事件参数负载: "+Arrays.toString(args));

        return"动作订单记账执行成功";
        }).to(StateEnum.STATE_FINISHED);

        //构建租金订单状态机
        stateMachine=stateMachineBuilder.build(StateMachineEnum.RENT_ORDER);
        }
```

##### 8、发送事件

```java
    @Test
public void promo(){
        //02、营销
        EventContextImpl<StateEnum, EventEnum> eventContext=EventContextImpl.getInstance();
        EventImpl<EventEnum> event=EventImpl.getInstance();
        event.eventId(EventEnum.EVENT_PROMO)
        .payload("订单: xxxxxxx","营销方案: 满100减50");
        eventContext.stateId(StateEnum.STATE_WAIT_PROMO)
        .event(event);
//        IStateContext<StateEnum, EventEnum> stateContext = stateMachine.fireEvent(eventContext);
        IStateContext<StateEnum, EventEnum> stateContext=stateMachine.fireEvent(StateEnum.STATE_WAIT_PROMO,EventEnum.EVENT_PROMO,"订单: xxxxxxx","营销方案: 满100减50");

        System.out.printf("执行后的状态[%s], 执行后的结果[%s]%n",stateContext.getStateId(),stateContext.getPayload());
        }

@Test
public void modifyPrice(){
        //03、修改金额
        EventContextImpl<StateEnum, EventEnum> eventContext=EventContextImpl.getInstance();
        EventImpl<EventEnum> event=EventImpl.getInstance();
        event.eventId(EventEnum.EVENT_MODIFY_PRICE)
        .payload("订单: xxxxxxx","修改金额方案: 将金额修改为50");
        eventContext.stateId(StateEnum.STATE_WAIT_PROMO)
        .event(event);
//        IStateContext<StateEnum, EventEnum> stateContext = stateMachine.fireEvent(eventContext);
        IStateContext<StateEnum, EventEnum> stateContext=stateMachine.fireEvent(StateEnum.STATE_WAIT_PROMO,EventEnum.EVENT_MODIFY_PRICE,"订单: xxxxxxx","修改金额方案: 将金额修改为50");

        System.out.printf("执行后的状态[%s], 执行后的结果[%s]%n",stateContext.getStateId(),stateContext.getPayload());
        }

@Test
public void balance(){
        //04、结算
        EventContextImpl<StateEnum, EventEnum> eventContext=EventContextImpl.getInstance();
        EventImpl<EventEnum> event=EventImpl.getInstance();
        event.eventId(EventEnum.EVENT_BALANCE)
        .payload("订单: xxxxxxx","结算: 计算后的金额10");
        eventContext.stateId(StateEnum.STATE_WAIT_BALANCE)
        .event(event);
//        IStateContext<StateEnum, EventEnum> stateContext = stateMachine.fireEvent(eventContext);
        IStateContext<StateEnum, EventEnum> stateContext=stateMachine.fireEvent(StateEnum.STATE_WAIT_BALANCE,EventEnum.EVENT_BALANCE,"订单: xxxxxxx","结算: 计算后的金额10");

        System.out.printf("执行后的状态[%s], 执行后的结果[%s]%n",stateContext.getStateId(),stateContext.getPayload());
        }

@Test
public void pay(){
        //05、支付
        EventContextImpl<StateEnum, EventEnum> eventContext=EventContextImpl.getInstance();
        EventImpl<EventEnum> event=EventImpl.getInstance();
        event.eventId(EventEnum.EVENT_PAY)
        .payload("订单: xxxxxxx","支付金额: 10");
        eventContext.stateId(StateEnum.STATE_WAIT_PAY)
        .event(event);
//        IStateContext<StateEnum, EventEnum> stateContext = stateMachine.fireEvent(eventContext);
        IStateContext<StateEnum, EventEnum> stateContext=stateMachine.fireEvent(StateEnum.STATE_WAIT_PAY,EventEnum.EVENT_PAY,"订单: xxxxxxx","支付金额: 10");

        System.out.printf("执行后的状态[%s], 执行后的结果[%s]%n",stateContext.getStateId(),stateContext.getPayload());
        }

@Test
public void booking(){
        //06、记账
        EventContextImpl<StateEnum, EventEnum> eventContext=EventContextImpl.getInstance();
        EventImpl<EventEnum> event=EventImpl.getInstance();
        event.eventId(EventEnum.EVENT_BOOKING)
        .payload("订单: xxxxxxx","记账: 用户积分、信誉分改动");
        eventContext.stateId(StateEnum.STATE_WAIT_BOOKING)
        .event(event);
//        IStateContext<StateEnum, EventEnum> stateContext = stateMachine.fireEvent(eventContext);
        IStateContext<StateEnum, EventEnum> stateContext=stateMachine.fireEvent(StateEnum.STATE_WAIT_BOOKING,EventEnum.EVENT_BOOKING,"订单: xxxxxxx","记账: 用户积分、信誉分改动");

        System.out.printf("执行后的状态[%s], 执行后的结果[%s]%n",stateContext.getStateId(),stateContext.getPayload());
        }

@Test
public void cancel(){
        //07、取消订单
        EventContextImpl<StateEnum, EventEnum> eventContext=EventContextImpl.getInstance();
        EventImpl<EventEnum> event=EventImpl.getInstance();
        event.eventId(EventEnum.EVENT_CANCEL)
        .payload("订单: xxxxxxx","取消订单理由: 点错了");
        eventContext.stateId(StateEnum.STATE_WAIT_PAY)
        .event(event);
//        IStateContext<StateEnum, EventEnum> stateContext = stateMachine.fireEvent(eventContext);
        IStateContext<StateEnum, EventEnum> stateContext=stateMachine.fireEvent(StateEnum.STATE_WAIT_PAY,EventEnum.EVENT_CANCEL,"订单: xxxxxxx","取消订单理由: 点错了");

        System.out.printf("执行后的状态[%s], 执行后的结果[%s]%n",stateContext.getStateId(),stateContext.getPayload());
        }

@Test
public void close(){
        //08、关闭订单
        EventContextImpl<StateEnum, EventEnum> eventContext=EventContextImpl.getInstance();
        EventImpl<EventEnum> event=EventImpl.getInstance();
        event.eventId(EventEnum.EVENT_CLOSE)
        .payload("订单: xxxxxxx","关闭订单理由: 点错了");
        eventContext.stateId(StateEnum.STATE_WAIT_PAY)
        .event(event);
//        IStateContext<StateEnum, EventEnum> stateContext = stateMachine.fireEvent(eventContext);
        IStateContext<StateEnum, EventEnum> stateContext=stateMachine.fireEvent(StateEnum.STATE_WAIT_PAY,EventEnum.EVENT_CLOSE,"订单: xxxxxxx","关闭订单理由: 点错了");

        System.out.printf("执行后的状态[%s], 执行后的结果[%s]%n",stateContext.getStateId(),stateContext.getPayload());
        }
```

#### Spring框架下使用

##### 1、添加依赖

```xml

<dependency>
    <groupId>io.github.zhdotm</groupId>
    <artifactId>zhm-statemachine-starter-web</artifactId>
    <version>1.1.2</version>
</dependency>
```

##### 2、修改配置

```yaml
zhm:
  statemachine:
    enable: true
```

##### 3、定义状态机组件

如果一个实现了ITransitionAdapter接口的类被@StateMachineComponent注解，且用@StateMachineCondition、@StateMachineAction指定了该类上的方法为条件判断方法和动作方法，那么这个类可被视为一个状态机组件。

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

##### 4、发送事件

```java
/**
 * 关闭订单
 */
@Test
public void close(){
        IStateContext<String, String> stateContext=StateMachineSupport
        .fireEvent("RENT_ORDER","STATE_WAIT_PROMO","EVENT_CLOSE","orderId:123456789");

        System.out.println(JSON.toJSONString(stateContext));
        }

/**
 * 取消订单
 */
@Test
public void cancel(){
        IStateContext<String, String> stateContext=StateMachineSupport
        .fireEvent("RENT_ORDER","STATE_WAIT_PAY","EVENT_CANCEL","orderId:123456789");

        System.out.println(JSON.toJSONString(stateContext));
        }

/**
 * 初始化订单
 */
@Test
public void init(){
        IStateContext<String, String> stateContext=StateMachineSupport
        .fireEvent("RENT_ORDER","STATE_WAIT_INIT","EVENT_INIT","orderId:111111","buyerId:2222222","commodityId:333333",5,500L);

        System.out.println(JSON.toJSONString(stateContext));
        }

/**
 * 营销订单
 */
@Test
public void promo(){
        IStateContext<String, String> stateContext=StateMachineSupport
        .fireEvent("RENT_ORDER","STATE_WAIT_PROMO","EVENT_PROMO","orderId:111111","couponId:222222");

        System.out.println(JSON.toJSONString(stateContext));
        }

/**
 * 修改订单金额
 */
@Test
public void modifyPrice(){
        IStateContext<String, String> stateContext=StateMachineSupport
        .fireEvent("RENT_ORDER","STATE_WAIT_PAY","EVENT_MODIFY_PRICE","orderId:111111",200L);

        System.out.println(JSON.toJSONString(stateContext));
        }

/**
 * 结算订单
 */
@Test
public void balance(){
        IStateContext<String, String> stateContext=StateMachineSupport
        .fireEvent("RENT_ORDER","STATE_WAIT_BALANCE","EVENT_BALANCE","orderId:111111");

        System.out.println(JSON.toJSONString(stateContext));
        }

/**
 * 支付订单
 */
@Test
public void pay(){
        IStateContext<String, String> stateContext=StateMachineSupport
        .fireEvent("RENT_ORDER","STATE_WAIT_PAY","EVENT_PAY","orderId:111111");

        System.out.println(JSON.toJSONString(stateContext));
        }

/**
 * 记账订单
 */
@Test
public void booking(){
        IStateContext<String, String> stateContext=StateMachineSupport
        .fireEvent("RENT_ORDER","STATE_WAIT_BOOKING","EVENT_BOOKING","orderId:111111");

        System.out.println(JSON.toJSONString(stateContext));
        }
```
