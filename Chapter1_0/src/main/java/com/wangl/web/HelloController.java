package com.wangl.web;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by seentech on 2016/12/29.
 */

@RestController
public class HelloController {

    String html = "hello";

    @RequestMapping("/hello")
    public String index(){
        return html;
    }
}
