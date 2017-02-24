package com.wangl.server;

import com.wangl.conf.MailConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by seentech on 2017/2/23.
 */
@Component
public class TestServer {


    private MailConfig mailConfig;

    @Autowired
    public TestServer(MailConfig mailConfig) {
        this.mailConfig = mailConfig;
    }


    public void testMail(){
        System.out.println(mailConfig.protocol);

        System.out.println("Hello world");
    }
}
