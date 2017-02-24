package com.wangl.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by seentech on 2017/2/23.
 */
@Component
public class ApplicationConf {

    public static String protocol;

    public static String host;

    public static int port;


    @Autowired
    public ApplicationConf(MailConfig mailConfig) {

        protocol = mailConfig.getProtocol();
        host = mailConfig.getHost();
        port = mailConfig.getPort();
    }
}
