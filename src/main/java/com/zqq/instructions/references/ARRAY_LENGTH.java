package com.zqq.instructions.references;

import com.zqq.instructions.base.InstructionNoOperands;
import com.zqq.runtimedata.Frame;
import com.zqq.runtimedata.OperandStack;
import com.zqq.runtimedata.heap.methodarea.Object;

/**
 * 获取数组长度的指令
 */
public class ARRAY_LENGTH extends InstructionNoOperands {

    @Override
    public void execute(Frame frame) {

        OperandStack stack = frame.operandStack();
        Object arrRef = stack.popRef();
        if (null == arrRef){
            throw new NullPointerException();
        }

        int arrLen = arrRef.arrayLength();
        stack.pushInt(arrLen);
    }

}
