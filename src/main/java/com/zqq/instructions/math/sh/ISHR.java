package com.zqq.instructions.math.sh;

import com.zqq.instructions.base.InstructionNoOperands;
import com.zqq.runtimedata.Frame;
import com.zqq.runtimedata.OperandStack;

/**
 * 算术右移,需要扩展符号位,使用最高位填充移位后左侧的空位
 * 11001101 => 11100110
 */
//arithmetic shift right int
public class ISHR extends InstructionNoOperands {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack();
        int v2 = stack.popInt();
        int v1 = stack.popInt();
        int s = v2 & 0x1f;
        int res = v1 >> s;
        stack.pushInt(res);
    }

}


