package com.zqq.instructions.math.add;

import com.zqq.instructions.base.InstructionNoOperands;
import com.zqq.runtimedata.Frame;
import com.zqq.runtimedata.OperandStack;

/**
 * 加法（add）指令
 * 减法（sub）指令
 * 乘法（mul）指令
 * 除法（div）指令
 * 求余（rem）指令
 * 取反（neg）指令
 */
//add double
public class DADD extends InstructionNoOperands {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack();
        double v1 = stack.popDouble();
        double v2 = stack.popDouble();
        double res = v1 + v2;
        stack.pushDouble(res);
    }

}
