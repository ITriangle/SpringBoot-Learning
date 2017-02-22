package com.wangl.conf;

import com.wangl.modle.ServerObj;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by seentech on 2017/2/22.
 */
@Configuration
public class ServerConf {


    @Bean
    public ServerObj serverObj(){
        return new ServerObj("STMP","localhost",999);
    }
}
