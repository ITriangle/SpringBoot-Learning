package com.wangl.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by seentech on 2017/2/22.
 */

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
