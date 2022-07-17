package com.zqq._native.java;

import com.zqq._native.NativeMethod;
import com.zqq._native.Registry;
import com.zqq.runtimedata.Frame;
import com.zqq.runtimedata.heap.methodarea.Object;
import com.zqq.runtimedata.heap.methodarea.StringPool;

public class _String {

    private final String jlString = "java/lang/String";

    public _String() {
        Registry.register(jlString, "intern", "()Ljava/lang/String;", new NativeMethod(this, "intern"));
        Registry.register(jlString,"registerNatives", "()V",new NativeMethod(this,"registerNatives"));
    }

    public void registerNatives(Frame frame) {
        // do nothing
    }

    public void intern(Frame frame){
        Object thiz = frame.localVars().getThis();
        Object interned = StringPool.internString(thiz);
        frame.operandStack().pushRef(interned);
    }

}
