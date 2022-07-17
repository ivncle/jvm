package com.zqq.instructions.loads.aload;

import com.zqq.instructions.base.InstructionNoOperands;
import com.zqq.runtimedata.Frame;
import com.zqq.runtimedata.heap.methodarea.Object;

public class ALOAD_1 extends InstructionNoOperands {

    @Override
    public void execute(Frame frame) {
        Object ref = frame.localVars().getRef(1);
        frame.operandStack().pushRef(ref);
    }

}
