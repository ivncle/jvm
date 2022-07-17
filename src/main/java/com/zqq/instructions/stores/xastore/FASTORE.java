package com.zqq.instructions.stores.xastore;

import com.zqq.instructions.base.InstructionNoOperands;
import com.zqq.runtimedata.Frame;
import com.zqq.runtimedata.OperandStack;
import com.zqq.runtimedata.heap.methodarea.Object;

public class FASTORE extends InstructionNoOperands {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack();
        float val = stack.popFloat();
        int idx = stack.popInt();
        Object arrRef = stack.popRef();

        checkNotNull(arrRef);
        float[] floats = arrRef.floats();
        checkIndex(floats.length, idx);
        floats[idx] = val;
    }

}
