package com.wangl.ioc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by seentech on 2017/2/24.
 */
@Component
public class BeanClass {


    @Autowired
    private PreBeanClass preBeanClass;

    private String callFun;


    public BeanClass() {

        preBeanClass = new PreBeanClass("PreBeanClass有参构造");

        callFun = "BeanClass无参构造";
    }

//    @Autowired
    public void setPreBeanClass(PreBeanClass preBeanClass) {
        this.preBeanClass = preBeanClass;

        callFun = "BeanClass set方法 ";
    }

    @Autowired
    public void setPreBeanClass() {
        preBeanClass = new PreBeanClass("BeanClass set方法 ");

        callFun = "BeanClass set方法 ";
    }

//    @Autowired
    public BeanClass(PreBeanClass preBeanClass) {
        this.preBeanClass = preBeanClass;

//        callFun = "BeanClass有参构造";
    }



    @Override
    public String toString() {
        return "BeanClass{" +
                "preBeanClass=" + preBeanClass +
                ", callFun='" + callFun + '\'' +
                '}';
    }
}
