package com.zqq.instructions.math.sh;

import com.zqq.instructions.base.InstructionNoOperands;
import com.zqq.runtimedata.Frame;
import com.zqq.runtimedata.OperandStack;

/***
 * 逻辑右移,或叫无符号右移运算符
 * 11001101 => 01100110
 */
public class IUSHR extends InstructionNoOperands {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack();
        int v2 = stack.popInt();
        int v1 = stack.popInt();
        int s = v2 & 0x1f;
        int res = v1 >>> s;
        stack.pushInt(res);
    }

}