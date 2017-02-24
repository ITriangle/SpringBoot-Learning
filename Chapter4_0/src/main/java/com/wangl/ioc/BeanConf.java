package com.wangl.ioc;

import org.springframework.context.annotation.Bean;

/**
 * Created by seentech on 2017/2/24.
 */
//@Configuration
public class BeanConf {

    @Bean
    public BeanClass beanClass(){

        PreBeanClass preBeanClass = new PreBeanClass("BeanConf 方法 ");

        return new BeanClass(preBeanClass);

    }
}
