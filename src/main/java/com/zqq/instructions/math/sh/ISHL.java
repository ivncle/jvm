package com.zqq.instructions.math.sh;

import com.zqq.instructions.base.InstructionNoOperands;
import com.zqq.runtimedata.Frame;
import com.zqq.runtimedata.OperandStack;

/**
 * int 左位移
 * 先从操作数栈中弹出两个int变量v2和v1。
 * v1是要进行位移操作的变量，v2指出要移位多少比特。
 */
//shift left int
public class ISHL extends InstructionNoOperands {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack();
        int v2 = stack.popInt();
        int v1 = stack.popInt();
        //int变量只有32位，所以只取val2的后5个比特就足够表示位移位数了
        int s = v2 & 0x1f;
        int res = v1 << s;
        stack.pushInt(res);
    }

}
