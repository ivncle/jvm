package com.zqq.instructions.math.sh;

import com.zqq.instructions.base.InstructionNoOperands;
import com.zqq.runtimedata.Frame;
import com.zqq.runtimedata.OperandStack;

/**
 * long类型
 */
public class LSHL extends InstructionNoOperands {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack();
        int v2 = stack.popInt();
        long v1 = stack.popLong();
        //32位
        int s = v2 & 0x3f;
        long res = v1 << s;
        stack.pushLong(res);

    }

}


