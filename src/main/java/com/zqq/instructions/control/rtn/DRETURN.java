package com.zqq.instructions.control.rtn;

import com.zqq.instructions.base.InstructionNoOperands;
import com.zqq.runtimedata.Frame;
import com.zqq.runtimedata.Thread;

public class DRETURN extends InstructionNoOperands {

    @Override
    public void execute(Frame frame) {
        Thread thread = frame.thread();
        Frame currentFrame = thread.popFrame();
        Frame invokerFrame = thread.topFrame();
        double val = currentFrame.operandStack().popDouble();
        invokerFrame.operandStack().pushDouble(val);
    }

}

