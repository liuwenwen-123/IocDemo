package com.example.mylibrary;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/***
 *  动态代理类
 */

public class Listenerandler  implements InvocationHandler {
    private  Object object;  // 相当于 mainctivity
    private  Method  myMethod;  // 真正执行的方法  click方法

    public Listenerandler(Object object, Method method) {
        this.object = object;
        this.myMethod = method;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return myMethod.invoke(object,args);
    }
}
