package com.zqq._native.sum;

import com.zqq.instructions.base.MethodInvokeLogic;
import com.zqq.runtimedata.Frame;
import com.zqq.runtimedata.heap.methodarea.Class;
import com.zqq.runtimedata.heap.methodarea.Method;
import com.zqq.runtimedata.heap.methodarea.Object;
import com.zqq.runtimedata.heap.methodarea.StringPool;

public class VM {

    public void initialize(Frame frame) {
        Class vmClass = frame.method().clazz();
        Object savedProps = vmClass.getRefVar("savedProps", "Ljava/util/Properties;");
        Object key = StringPool.jString(vmClass.loader(), "foo");
        Object val = StringPool.jString(vmClass.loader(), "bar");

        frame.operandStack().pushRef(savedProps);
        frame.operandStack().pushRef(key);
        frame.operandStack().pushRef(val);

        Class propsClass = vmClass.loader().loadClass("java/util/Properties");
        Method setPropMethod = propsClass.getInstanceMethod("setProperty",
                "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;");
        MethodInvokeLogic.invokeMethod(frame, setPropMethod);
    }

}
