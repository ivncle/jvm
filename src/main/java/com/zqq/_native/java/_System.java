package com.zqq._native.java;

import com.zqq._native.NativeMethod;
import com.zqq._native.Registry;
import com.zqq.runtimedata.Frame;
import com.zqq.runtimedata.LocalVars;
import com.zqq.runtimedata.heap.methodarea.Class;
import com.zqq.runtimedata.heap.methodarea.Object;

public class _System {

    private final String jlSystem = "java/lang/System";

    public _System() {
        Registry.register(jlSystem, "arraycopy", "()Ljava/lang/String;", new NativeMethod(this, "arraycopy"));
        Registry.register(jlSystem,"registerNatives", "()V",new NativeMethod(this,"registerNatives"));
    }

    public void registerNatives(Frame frame) {
        // do nothing
    }

    public void arraycopy(Frame frame) {
        LocalVars vars = frame.localVars();
        Object src = vars.getRef(0);
        int srcPos = vars.getInt(1);
        Object dest = vars.getRef(2);
        int destPos = vars.getInt(4);
        int length = vars.getInt(4);

        if (null == src || dest == null) {
            throw new NullPointerException();
        }

        if (!checkArrayCopy(src, dest)) {
            throw new ArrayStoreException();
        }

        if (srcPos < 0 || destPos < 0 || length < 0 ||
                srcPos + length > src.arrayLength() ||
                destPos + length > dest.arrayLength()) {
            throw new IndexOutOfBoundsException();
        }

        System.arraycopy(src, srcPos, dest, destPos, length);

        //todo

    }

    public boolean checkArrayCopy(Object src, Object dest) {
        Class srcClass = src.clazz();
        Class destClass = dest.clazz();

        if (!srcClass.isArray() || !destClass.isArray()) {
            return false;
        }

        if (srcClass.componentClass().IsPrimitive() || destClass.componentClass().IsPrimitive()) {
            return srcClass == destClass;
        }

        return true;

    }

}
