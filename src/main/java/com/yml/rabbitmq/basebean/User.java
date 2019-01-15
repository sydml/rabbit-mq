package com.yml.rabbitmq.basebean;

import java.io.Serializable;

/**
 * Created by Yuming-Liu
 * 日期： 2019-01-15
 * 时间： 20:39
 */
public class User implements Serializable{
    private String username;
    private String password;
    private Integer age;

    public User() {
    }

    public User(String username, String password, Integer age) {
        this.username = username;
        this.password = password;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
