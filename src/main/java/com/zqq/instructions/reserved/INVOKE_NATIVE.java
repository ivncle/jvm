package com.zqq.instructions.reserved;

import com.zqq._native.NativeMethod;
import com.zqq._native.Registry;
import com.zqq.instructions.base.InstructionNoOperands;
import com.zqq.runtimedata.Frame;
import com.zqq.runtimedata.heap.methodarea.Method;

public class INVOKE_NATIVE extends InstructionNoOperands {

    @Override
    public void execute(Frame frame) {
        Method method = frame.method();
        String className = method.clazz().name();
        String methodName = method.name();
        String methodDescriptor = method.descriptor();

        NativeMethod nativeMethod = Registry.findNativeMethod(className, methodName, methodDescriptor);
        if (null == nativeMethod) {
            String methodInfo = className + "." + methodName + methodDescriptor;
            throw new UnsatisfiedLinkError(methodInfo);
        }

        nativeMethod.invoke(frame);

    }

}
