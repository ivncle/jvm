package com.zqq.instructions.loads.xaload;

import com.zqq.instructions.base.InstructionNoOperands;
import com.zqq.runtimedata.Frame;
import com.zqq.runtimedata.OperandStack;
import com.zqq.runtimedata.heap.methodarea.Object;

/**
 * 将引用型数组指定索引的值推送至栈顶
 */
public class AALOAD extends InstructionNoOperands {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack();
        int idx = stack.popInt();
        Object arrRef = stack.popRef();

        checkNotNull(arrRef);
        Object[] refs = arrRef.refs();
        checkIndex(refs.length, idx);
        stack.pushRef(refs[idx]);
    }

}

