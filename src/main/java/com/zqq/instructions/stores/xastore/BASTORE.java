package com.zqq.instructions.stores.xastore;

import com.zqq.instructions.base.InstructionNoOperands;
import com.zqq.runtimedata.Frame;
import com.zqq.runtimedata.OperandStack;
import com.zqq.runtimedata.heap.methodarea.Object;

public class BASTORE extends InstructionNoOperands {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack();
        int val = stack.popInt();
        int idx = stack.popInt();
        Object arrRef = stack.popRef();

        checkNotNull(arrRef);
        byte[] bytes = arrRef.bytes();
        checkIndex(bytes.length, idx);
        bytes[idx] = (byte) val;

    }

}
