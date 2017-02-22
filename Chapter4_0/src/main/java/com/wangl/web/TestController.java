package com.wangl.web;

import com.wangl.conf.MailConfig;
import com.wangl.modle.ServerObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by seentech on 2017/2/22.
 */

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    public ServerObj serverObj;

    @Autowired
    private MailConfig mailConfig;

    @RequestMapping("/mail")
    public String getMailConfig() {
        return mailConfig.toString();
    }

    @RequestMapping("/server")
    public String getServerConfig() {
        return serverObj.toString();
    }
}
