# zhm-statemachine

## 背景

公司内订单系统、合同系统等相关业务其内部业务逻辑复杂，难以光从代码中清晰得到相关业务状态流转。

参考：https://blog.csdn.net/significantfrank/article/details/104996419

### 使用场景

适合在业务流程较长场景较为复杂的需求中使用。

### 与cola-statemachine的区别

zhm-statemachine灵感来源于https://github.com/alibaba/COLA/tree/master/cola-components/cola-component-statemachine
，并在其设计思想的前提下，做了部分改动。

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
        //待初始化 -> 待营销
        stateMachineBuilder
                .createExternalTransition()
                .from(StateEnum.STATE_WAIT_INIT)
                .on(EventEnum.EVENT_INIT)
                .when(ConditionEnum.IS_ABLE_INIT,
                eventContext->{
                System.out.println("检查能否初始化订单");
                IEvent<EventEnum> event=eventContext.getEvent();
        Object[]payload=event.getPayload();
        System.out.println("收到事件参数负载: "+Arrays.toString(payload));

        return Boolean.TRUE;
        }).perform(ActionEnum.ACTION_INIT,
        args->{
        System.out.println("执行初始化订单动作");
        System.out.println("收到事件参数负载: "+Arrays.toString(args));

        return"动作初始化执行成功";
        })
        .to(StateEnum.STATE_WAIT_PROMO)
        .build();
```

创建内部流转

```java
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
```

创建批量流转

```java
        //待初始化、待营销、待结算、待支付 -> 取消
        stateMachineBuilder
                .createExternalTransition()
                .from(StateEnum.STATE_WAIT_INIT,
                StateEnum.STATE_WAIT_PROMO,
                StateEnum.STATE_WAIT_BALANCE,
                StateEnum.STATE_WAIT_PAY)
                .on(EventEnum.EVENT_CANCEL)
                .when(ConditionEnum.IS_ABLE_CANCEL,
                eventContext->{
                System.out.println("检查能否取消订单");
                IEvent<EventEnum> event=eventContext.getEvent();
        Object[]payload=event.getPayload();
        System.out.println("收到事件参数负载: "+Arrays.toString(payload));

        return Boolean.TRUE;
        }).perform(ActionEnum.ACTION_CANCEL,
        args->{
        System.out.println("执行取消订单动作");
        System.out.println("收到事件参数负载: "+Arrays.toString(args));

        return"动作取消执行成功";
        })
        .to(StateEnum.STATE_CANCELED)
        .build();
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
    /**
 * 02、营销（事件上下文方式）
 */
@Test
public void promo1(){
        IEventContextBuilder<StateEnum, EventEnum> eventContextBuilder=EventContextFactory.create();
        IEventBuilder<EventEnum> eventBuilder=EventFactory.create();
        IEvent<EventEnum> event=eventBuilder
        .payload("订单: xxxxxxx","营销方案: 满100减50")
        .id(EventEnum.EVENT_PROMO)
        .build();
        IEventContext<StateEnum, EventEnum> eventContext=eventContextBuilder
        .from(StateEnum.STATE_WAIT_PROMO)
        .on(event)
        .build();

        IStateContext<StateEnum, EventEnum> stateContext=stateMachine.fireEvent(eventContext);

        System.out.printf("执行后的状态[%s], 执行后的结果[%s]%n",stateContext.getStateId(),stateContext.getPayload());
        }
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

### 基本使用

#### 1、原生场景下使用

##### 1.1、引入依赖

```xml
<dependency>
    <groupId>io.github.zhdotm</groupId>
    <artifactId>zhm-statemachine-model</artifactId>
    <version>1.1.5</version>
</dependency>
```

##### 1.2、构建状态机

```java
//1、利用状态机工厂创建一个状态机构造器
IStateMachineBuilder<String, String, String, String, String> stateMachineBuilder = StateMachineFactory.create();

//2、创建转换
//2.1、创建外部转换
stateMachineBuilder
        .createExternalTransition()
        //2.1.1、排序
        .sort(1)
        //2.1.2、来源状态
        .from("state1", "state2")
        //2.1.3、发生的事件
        .on("event1")
        //2.1.4、条件判断（判断事件能否通过）
        .when("condition1",
                eventContext -> {
                    //收到的事件
                    IEvent<String> event = eventContext.getEvent();
                    //事件ID
                    String eventId = event.getEventId();
                    //事件携带的参数
                    Object[] objs = event.getPayload();
                    //收到事件时候的状态ID
                    String stateId = eventContext.getStateId();

                    return Boolean.TRUE;
                })
        //2.1.5、条件通过后，要执行的动作
        .perform("action1", objs -> {
            //objs是事件携带的参数
            //经过一系列处理后，返回处理结果

            return "ok";
        })
        //2.1.6、转换后的状态
        .to("state3")
        .build();
//2.2、创建内部转换
stateMachineBuilder.createInternalTransition()
        //2.2.1、排序
        .sort(2)
        //2.2.2、来源状态
        .from("state1")
        //2.2.3、事件
        .on("event2")
        //2.2.4、条件判断
        .when("condition2",
                eventContext -> {
                    return Boolean.TRUE;
                })
        //2.2.5、动作
        .perform("action2",
                objs -> null)
        .build();

//设置状态机ID
IStateMachine<String, String, String, String, String> stateMachine = stateMachineBuilder
        .build("stateMachine1");
```

##### 1.3、发送事件

```java
//发送事件
IStateContext<String, String> stateContext = stateMachine.fireEvent("state1", "event1", "参数1", "参数2", "参数3");
//事件上下文
IEventContext<String, String> eventContext = stateContext.getEventContext();
//转换后的状态
String stateId = stateContext.getStateId();
//动作执行结果
Object payload = stateContext.getPayload();
```

#### 2、Spring框架下使用

##### 2.1、引入依赖

```xml
<dependency>
    <groupId>io.github.zhdotm</groupId>
    <artifactId>zhm-statemachine-starter-web</artifactId>
    <version>1.1.5</version>
</dependency>
```

##### 2.2、修改配置

enable：是否开启状态机自动组装

print：组装状态机成功后是否打印状态机内部结构

```yaml
zhm:
  statemachine:
    enable: true
    print: true
```

##### 2.3、定义状态机组件

如果一个实现了ITransitionAdapter接口的类被@StateMachineComponent注解，且用@StateMachineCondition、@StateMachineAction指定了该类上的方法为条件判断方法和动作方法，那么这个类可被视为一个状态机组件。多个相同stateMachineId的状态机组件构成一个状态机。

```java
@Component
@StateMachineComponent(
        stateMachineId = "stateMachine1",
        type = TransitionTypeEnum.EXTERNAL,
        from = {"state1", "state2"},
        on = "event1",
        to = "state3"
)
public class AStateMachineComponent implements ITransitionAdapter {

    @StateMachineCondition(conditionId = "isAbleToDoThis")
    public Boolean check(String input) {
        System.out.println("检查能否做某个事");

        return Boolean.TRUE;
    }

    @StateMachineAction(actionId = "doSomething")
    public String execute(String input) {
        System.out.println("做某个事");

        return "事情的执行结果";
    }

}
```

### 案例

以订单系统为例

#### 状态流转图

![订单状态流转图.drawio](https://raw.githubusercontent.com/zhdotm/picture-storage/main/%E8%AE%A2%E5%8D%95%E7%8A%B6%E6%80%81%E6%B5%81%E8%BD%AC%E5%9B%BE.drawio.png)

#### [原生场景下使用](https://github.com/zhdotm/zhm-statemachine/tree/main/zhm-statemachine-model)

#### [Spring框架下使用](https://github.com/zhdotm/zhm-statemachine/tree/main/zhm-statemachine-starter/zhm-statemachine-starter-web)
