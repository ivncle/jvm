package com.zqq.instructions.conversions.d2x;

import com.zqq.instructions.base.InstructionNoOperands;
import com.zqq.runtimedata.Frame;
import com.zqq.runtimedata.OperandStack;

/**
 * i2x 系列指令把 int 变量强制转换成其他类型
 * l2x 系列指令把 long 变量强制转换成其他类型
 * f2x 系列指令把 float 变量强制转换成其他型
 * d2x 系列指令把 double 变量强制转换成其他类型
 */
//convert double to float
public class D2F extends InstructionNoOperands {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack();
        double d = stack.popDouble();
        float f = (float) d;
        stack.pushFloat(f);
    }

}
