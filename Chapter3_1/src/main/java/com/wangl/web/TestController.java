package com.wangl.web;

import org.springframework.web.bind.annotation.*;

/**
 * Created by seentech on 2017/2/23.
 */
@RestController
public class TestController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public String hello(@RequestParam String name) {
        return "Hello " + name;
    }
}
