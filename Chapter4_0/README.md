# SpringBoot 自动配置和单元测试

## 一.自动配置
自动配置,可以读取外部的配置文件,直接实例化 Bean.

### 1.在resources 中新建配置文件 mail.properties

```
mail.protocol=STMP
mail.host=localhost
mail.port=25

```

### 2.创建 MailConfig 读取配置文件

```java
@Component //不加这个注解的话, 使用@Autowired 就不能注入进去了
@PropertySource("classpath:mail.properties") //说明当前的配置文件路径
public class MailConfig {


    @Value("${mail.protocol}")
    private String protocol;

    @Value("${mail.host}")
    private String host;

    @Value("${mail.port}")
    private int port;

    @Override
    public String toString() {
        return "MailConfig{" +
                "protocol='" + protocol + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                '}';
    }
}
```

### 3.编写测试用例
测试上面的注解必须的加上.

```java
/**
 * 测试时,最好在测试类上加如下两个注解
 */
@RunWith(SpringRunner.class) //告诉Junit运行使用Spring 的单元测试支持；SpringRunner是SpringJunit4ClassRunner新的名称，只是视觉上看起来更简单了。
@SpringBootTest//该注解可以在一个测试类指定运行Spring Boot为基础的测试。
public class ApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    public ServerObj serverObj;

    @Test
    public void test1(){
        System.out.println(serverObj.toString());
    }

    @Autowired
    public MailConfig mailConfig;

    @Test
    public void test2(){
        System.out.println(mailConfig.toString());
    }

}
```


## 二.单元测试
普通的不依赖于环境的单元测试是没有问题的.但是当依赖于SpringBoot 上下文中时,就需要完整的单元测试说明.

```java
/**
 * 测试时,最好在测试类上加如下两个注解
 */
@RunWith(SpringRunner.class)

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)

public class MyTest{

    // ...

}
```

- `@RunWith(SpringRunner.class)`: 告诉Junit运行使用Spring 的单元测试支持
- `SpringRunner` 是 `SpringJunit4ClassRunner` 新的名称，只是视觉上看起来更简单了
- `@SpringBootTest` : 该注解可以在一个测试类指定运行 Spring Boot 为基础的测试.当然还可以配置一些属性.

当然不仅仅就这么简单了，SpringBoot 1.4 在单元测试还有另外一些特性，大家可以在去官方看看文档，比如还有 `@JsonTest`，`@DataJpaTest` 等。