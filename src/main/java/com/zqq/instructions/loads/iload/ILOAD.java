package com.zqq.instructions.loads.iload;

import com.zqq.instructions.base.InstructionIndex8;
import com.zqq.runtimedata.Frame;

//load int from local variable
public class ILOAD extends InstructionIndex8 {

    @Override
    public void execute(Frame frame) {
        int val = frame.localVars().getInt(this.idx);
        frame.operandStack().pushInt(val);
    }
}

