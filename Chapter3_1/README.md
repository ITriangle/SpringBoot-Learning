# Spring Boot 使用AOP处理LOG
AOP(Aspect Oriented Programming)的缩写，意为：面向切面编程，通过预编译方式和运行期动态代理实现程序功能的统一维护的一种技术。AOP是Spring框架中的一个重要内容，它通过对既有程序定义一个切入点，然后在其前后切入不同的执行内容，比如常见的有：打开数据库连接/关闭数据库连接、打开事务/关闭事务、记录日志等。基于AOP不会破坏原来程序逻辑，因此它可以很好的对业务逻辑的各个部分进行隔离，从而使得业务逻辑各部分之间的耦合度降低，提高程序的可重用性，同时提高了开发的效率。

## 一.Maven

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```


## 二.引入AOP依赖
在完成了引入AOP依赖包后，一般来说并不需要去做其他配置。也许在Spring中使用过注解配置方式的人会问是否需要在程序主类中增加 `@EnableAspectJAutoProxy` 来启用，实际并不需要。

可以看下面关于AOP的默认配置属性，其中spring.aop.auto属性默认是开启的，也就是说只要引入了AOP依赖后，默认已经增加了` @EnableAspectJAutoProxy`。

```
# AOP
spring.aop.auto=true # Add @EnableAspectJAutoProxy.
spring.aop.proxy-target-class=false # Whether subclass-based (CGLIB) proxies are to be created (true) as
 opposed to standard Java interface-based proxies (false).
```

而当我们需要使用CGLIB来实现AOP的时候，需要配置`spring.aop.proxy-target-class=true`，不然默认使用的是标准Java的实现。

## 三.实现Web层的日志切面
实现AOP的切面主要有以下几个注解：

- 使用`@Aspect`注解将一个java类定义为切面类
- 使用`@Pointcut`定义一个切入点，可以是一个规则表达式，比如下例中某个package下的所有函数，也可以是一个注解等
- 根据需要在切入点不同位置的切入内容
    - 使用`@Before`在切入点开始处切入内容
    - 使用`@After`在切入点结尾处切入内容
    - 使用`@AfterReturning`在切入点`return`内容之后切入内容（可以用来对处理返回值做一些加工处理）
    - 使用`@Around`在切入点前后切入内容，并自己控制何时执行切入点自身的内容
    - 使用`@AfterThrowing`用来处理当切入内容部分抛出异常之后的处理逻辑
- 使用`@Component`,在SpringBoot Context 实例为Bean

```java
@Aspect
@Order(5)
@Component
public class WebLogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(public * com.wangl.web..*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        startTime.set(System.currentTimeMillis());

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));

    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        logger.info("RESPONSE : " + ret);
        logger.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
    }
}
```

### 1.AOP切面中的同步问题
上面代码中加入了请求开始到请求结束的耗时记录,做法是:

在WebLogAspect切面中，分别通过doBefore和doAfterReturning两个独立函数实现了切点头部和切点返回后执行的内容，并在doBefore处记录时间，并在doAfterReturning处通过当前时间与开始处记录的时间计算得到请求处理的消耗时间。

那么我们是否可以在WebLogAspect切面中定义一个成员变量来给doBefore和doAfterReturning一起访问呢？是否会有同步问题呢？

的确，直接在这里定义基本类型会有同步问题，所以我们可以引入`ThreadLocal`对象

### 2.AOP切面的优先级
由于通过 `AOP` 实现，程序得到了很好的解耦，但是也会带来一些问题，比如：我们可能会对 Web 层做多个切面，校验用户，校验头信息等等，这个时候经常会碰到切面的处理顺序问题。

所以，我们需要定义每个切面的优先级，我们需要 `@Order(i)` 注解来标识切面的优先级。**i的值越小，优先级越高**。假设我们还有一个切面是`CheckNameAspect`用来校验 `name` 必须为`wangl`，我们为其设置 `@Order(10)`，而上文中 WebLogAspect 设置为 `@Order(5)`，所以 WebLogAspect 有更高的优先级，这个时候执行顺序是这样的：

- 在`@Before`中优先执行`@Order(5)`的内容，再执行`@Order(10)`的内容
- 在`@After`和`@AfterReturning`中优先执行`@Order(10)`的内容，再执行`@Order(5)`的内容

所以可以这样子总结：

- **在切入点前的操作，按order的值由小到大执行**
- **在切入点后的操作，按order的值由大到小执行**

### 3.Pointcut 表达式例子

#### 3.1.方法标签匹配方式
假设定义了接口EmployeeManager接口

1. 匹配EmployeeManger接口中所有的方法:`execution(* com.howtodoinjava.EmployeeManager.*( .. ))` 
2. 当切面方法和EmployeeManager接口在相同的包内，如果切入点表达式匹配所有所有方法，则表达式可以改成:`execution(* EmployeeManager.*( .. ))`
3. 匹配EmployeeManager接口的所有public方法:`execution(public * EmployeeManager.*(..))`
4. 匹配EmployeeManager接口中权限为public并返回类型为EmployeeDTO的所有方法:`execution(public EmployeeDTO EmployeeManager.*(..))`
5. 匹配EmployeeManager接口中权限为public并返回类型为EmployeeDTO，第一个参数为EmployeeDTO类型的所有方法:`execution(public EmployeeDTO EmployeeManager.*(EmployeeDTO, ..))`
6. 匹配EmployeeManager接口中权限为public、返回类型为EmployeeDTO，参数明确定义为EmployeeDTO,Integer的所有方法:`execution(public EmployeeDTO EmployeeManager.*(EmployeeDTO, Integer))`

#### 3.2.类型标签匹配方式

1. 匹配在com.howtodoinjava包下所有类型中所有的方法:`within(com.howtodoinjava.*)`
2. 匹配在com.howtodoinjava包以及其子包下所有类型中所有的方法:`within(com.howtodoinjava..*)`
3. 匹配其他包一个类下的所有方法:`within(com.howtodoinjava.EmployeeManagerImpl)`
4. 匹配同一个包下一个类下的所有方法:`within(EmployeeManagerImpl)`
5. 匹配一个接口下的所有继承者的所有方法:`within(EmployeeManagerImpl+)`

#### 3.3.bean名字匹配模式
匹配所有以Manager结尾的beans中的所有方法: `bean(*Manager)`

#### 3.4.切入点表达式拼接
在AspectJ中，切入点表达式可以通过&&，||，!等操作符进行拼接。

这个例子表示匹配以Manager结尾或者以DAO结尾的beans中所有的方法:`bean(*Manager) || bean(*DAO)`
