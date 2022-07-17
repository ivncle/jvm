package com.zqq._native.java;

import com.zqq._native.NativeMethod;
import com.zqq._native.Registry;
import com.zqq.runtimedata.Frame;

public class _Float {

    private final String jlFloat = "java/lang/Float";

    public _Float() {
        Registry.register(jlFloat, "floatToRawIntBits", "(F)I", new NativeMethod(this, "floatToRawIntBits"));
        Registry.register(jlFloat,"intBitsToFloat", "(I)F",new NativeMethod(this,"intBitsToFloat"));
        Registry.register(jlFloat,"registerNatives", "()V",new NativeMethod(this,"registerNatives"));
    }

    public void registerNatives(Frame frame) {
        // do nothing
    }

    public void floatToRawIntBits(Frame frame){
        float val = frame.localVars().getFloat(0);
        frame.operandStack().pushInt((int) val);
    }

    public void intBitsToFloat(Frame frame){
        int val = frame.localVars().getInt(0);
        frame.operandStack().pushFloat(val);
    }

}
