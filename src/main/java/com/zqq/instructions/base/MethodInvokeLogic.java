package com.zqq.instructions.base;

import com.zqq.runtimedata.Frame;
import com.zqq.runtimedata.Slot;
import com.zqq.runtimedata.Thread;
import com.zqq.runtimedata.heap.methodarea.Method;

/**
 * 方法调用
 */
public class MethodInvokeLogic {

    public static void invokeMethod(Frame invokerFrame, Method method) {
        Thread thread = invokerFrame.thread();
        Frame newFrame = thread.newFrame(method);
        thread.pushFrame(newFrame);

        int argSlotCount = method.argSlotCount();
        //弹入参数
        if (argSlotCount > 0) {
            for (int i = argSlotCount - 1; i >= 0; i--) {
                Slot slot = invokerFrame.operandStack().popSlot();
                newFrame.localVars().setSlot(i, slot);
            }
        }

    }

}
