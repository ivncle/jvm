package com.zqq.instructions.constants.consts;

import com.zqq.instructions.base.InstructionNoOperands;
import com.zqq.runtimedata.Frame;

public class ICONST_0 extends InstructionNoOperands {

    @Override
    public void execute(Frame frame) {
        frame.operandStack().pushInt(0);
    }
}

