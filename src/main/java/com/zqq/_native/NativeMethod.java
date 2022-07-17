package com.zqq._native;

import com.zqq.runtimedata.Frame;

import java.lang.reflect.Method;

public class NativeMethod {

    private String methodName;
    private Object obj;

    public NativeMethod(Object obj, String methodName) {
        this.methodName = methodName;
        this.obj = obj;
    }

    public void invoke(Frame frame) {
        try {
            Method method = obj.getClass().getMethod(methodName, frame.getClass());
            method.invoke(obj, frame);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
