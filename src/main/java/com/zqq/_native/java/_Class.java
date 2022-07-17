package com.zqq._native.java;

import com.zqq._native.NativeMethod;
import com.zqq._native.Registry;
import com.zqq.runtimedata.Frame;
import com.zqq.runtimedata.LocalVars;
import com.zqq.runtimedata.OperandStack;
import com.zqq.runtimedata.heap.ClassLoader;
import com.zqq.runtimedata.heap.methodarea.Class;
import com.zqq.runtimedata.heap.methodarea.Object;
import com.zqq.runtimedata.heap.methodarea.StringPool;

public class _Class {

    private final String jlClass = "java/lang/Class";
    //将本地方法注册到注册表
    public _Class() {
        Registry.register(jlClass, "getPrimitiveClass", "(Ljava/lang/String;)Ljava/lang/Class;", new NativeMethod(this, "getPrimitiveClass"));
        Registry.register(jlClass, "getName0", "()Ljava/lang/String;", new NativeMethod(this, "getName0"));
        Registry.register(jlClass, "desiredAssertionStatus0", "(Ljava/lang/Class;)Z", new NativeMethod(this, "desiredAssertionStatus0"));
        Registry.register(jlClass, "registerNatives", "()V", new NativeMethod(this, "registerNatives"));
    }

    public void registerNatives(Frame frame) {
        // do nothing
    }

    public void getPrimitiveClass(Frame frame) {
        Object nameObj = frame.localVars().getRef(0);
        String name = StringPool.goString(nameObj);

        ClassLoader loader = frame.method().clazz().loader();
        Object jClass = loader.loadClass(name).jClass();

        frame.operandStack().pushRef(jClass);
    }

    public void getName0(Frame frame) {
        Object thiz = frame.localVars().getThis();
        Class clazz = (Class) thiz.extra();

        String name = "虚拟机本地方法getName0获取类名：" + clazz.javaName();
        Object nameObj = StringPool.jString(clazz.loader(), name);

        frame.operandStack().pushRef(nameObj);
    }

    public void desiredAssertionStatus0(Frame frame) {
        frame.operandStack().pushBoolean(false);
    }

    public void isInterface(Frame frame) {
        LocalVars vars = frame.localVars();
        Object thiz = vars.getThis();
        Class clazz = (Class) thiz.extra();

        OperandStack stack = frame.operandStack();
        stack.pushBoolean(clazz.isInterface());
    }

    public void isPrimitive(Frame frame) {
        LocalVars vars = frame.localVars();
        Object thiz = vars.getThis();
        Class clazz = (Class) thiz.extra();

        OperandStack stack = frame.operandStack();
        stack.pushBoolean(clazz.IsPrimitive());
    }

}
