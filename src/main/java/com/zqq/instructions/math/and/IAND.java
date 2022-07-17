package com.zqq.instructions.math.and;

import com.zqq.instructions.base.InstructionNoOperands;
import com.zqq.runtimedata.Frame;
import com.zqq.runtimedata.OperandStack;

/**
 * 按位与（and）
 * 按位或（or）
 * 按位异或（xor）
 */
//boolean and int
public class IAND extends InstructionNoOperands {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack();
        int v2 = stack.popInt();
        int v1 = stack.popInt();
        int res = v1 & v2;
        stack.pushInt(res);
    }
}

