package com.zqq.instructions.loads.lload;

import com.zqq.instructions.base.InstructionIndex8;
import com.zqq.runtimedata.Frame;

public class LLOAD extends InstructionIndex8 {

    @Override
    public void execute(Frame frame) {
        Long val = frame.localVars().getLong(this.idx);
        frame.operandStack().pushLong(val);
    }

}
