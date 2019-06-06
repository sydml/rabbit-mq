package com.yml.rabbitmq.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Yuming-Liu
 * 日期： 2018-08-08
 * 时间： 22:50
 */
public class JDKDynamicProxy implements InvocationHandler {

    private static JDKDynamicProxy DYNAMIC_PROXY = null;

    private JDKDynamicProxy() {
    }

    public static JDKDynamicProxy getDynamicProxy() {
        return DYNAMIC_PROXY == null ? new JDKDynamicProxy() : DYNAMIC_PROXY;
    }

    private Object target;

    public JDKDynamicProxy(Object target) {
        this.target = target;
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy() {
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        before();
        Object result = method.invoke(target, args);
        after();
        return result;


    }

    private void before() {
        System.out.println("调用前");
    }

    private void after() {
        System.out.println("调用后");
    }
}
