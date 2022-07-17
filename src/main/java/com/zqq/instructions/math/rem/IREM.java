package com.zqq.instructions.math.rem;

import com.zqq.instructions.base.InstructionNoOperands;
import com.zqq.runtimedata.Frame;
import com.zqq.runtimedata.OperandStack;

//remainder int
public class IREM extends InstructionNoOperands {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack();
        int v2 = stack.popInt();
        int v1 = stack.popInt();
        if (v2 == 0) {
            throw new ArithmeticException("/ by zero");
        }
        int res = v1 % v2;
        stack.pushInt(res);
    }

}