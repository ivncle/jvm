package com.zqq.instructions.loads.aload;

import com.zqq.instructions.base.InstructionIndex8;
import com.zqq.runtimedata.Frame;
import com.zqq.runtimedata.heap.methodarea.Object;

//load reference from local variable
public class ALOAD extends InstructionIndex8 {

    @Override
    public void execute(Frame frame) {
        Object ref = frame.localVars().getRef(this.idx);
        frame.operandStack().pushRef(ref);
    }


}

