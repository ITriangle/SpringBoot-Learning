package com.wangl.ioc;

import org.springframework.stereotype.Component;

/**
 * Created by seentech on 2017/2/24.
 */
@Component
public class PreBeanClass {

    private String preCallFun;

    public PreBeanClass() {
        preCallFun = "PreBeanClass无参构造";
    }

    public PreBeanClass(String preCallFun) {
        this.preCallFun = preCallFun;
    }

    @Override
    public String toString() {
        return "PreBeanClass{" +
                "preCallFun='" + preCallFun + '\'' +
                '}';
    }
}
