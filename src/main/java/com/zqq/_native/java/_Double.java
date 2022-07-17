package com.zqq._native.java;

import com.zqq._native.NativeMethod;
import com.zqq._native.Registry;
import com.zqq.runtimedata.Frame;

public class _Double {

    private final String jlDouble = "java/lang/Double";

    public _Double() {
        Registry.register(jlDouble, "doubleToRawLongBits", "(D)J", new NativeMethod(this, "doubleToRawLongBits"));
        Registry.register(jlDouble,"longBitsToDouble", "(J)D",new NativeMethod(this,"longBitsToDouble"));
        Registry.register(jlDouble,"registerNatives", "()V",new NativeMethod(this,"registerNatives"));
    }

    public void registerNatives(Frame frame) {
        // do nothing
    }

    public void doubleToRawLongBits(Frame frame) {
        double val = frame.localVars().getDouble(0);
        frame.operandStack().pushLong((long) val);
    }

    public void longBitsToDouble(Frame frame) {
        long val = frame.localVars().getLong(0);
        frame.operandStack().pushDouble(val);
    }

}
