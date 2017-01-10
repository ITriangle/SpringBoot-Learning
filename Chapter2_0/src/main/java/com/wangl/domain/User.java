package com.wangl.domain;

import java.io.Serializable;

/**
 * Created by seentech on 2017/1/10.
 */
public class User implements Serializable{

    private static final long serialVersionUID = -1L;

    private String usrname;
    private Integer age;

    public User(String usrname, Integer age) {
        this.usrname = usrname;
        this.age = age;
    }

    public String getUsrname() {
        return usrname;
    }

    public void setUsrname(String usrname) {
        this.usrname = usrname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
