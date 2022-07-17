package com.zqq.instructions.loads.fload;

import com.zqq.instructions.base.InstructionIndex8;
import com.zqq.runtimedata.Frame;

//load float from local variable
public class FLOAD extends InstructionIndex8 {

    @Override
    public void execute(Frame frame) {
        Float val = frame.localVars().getFloat(this.idx);
        frame.operandStack().pushFloat(val);
    }

}

