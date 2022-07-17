package com.zqq.instructions.base;

import com.zqq.runtimedata.Frame;
import com.zqq.runtimedata.OperandStack;

/**
 * BranchInstruction 表示跳转指令，Offset 字段存放跳转偏移量。
 * FetchOperands（）方法从字节码中读取一个 int16 整数，转成 int 后赋给 Offset 字段。
 */
public class InstructionBranch implements Instruction {

    protected int offset;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        this.offset = reader.readShort();
    }

    @Override
    public void execute(Frame frame) {

    }

    protected boolean _acmp(Frame frame){
        OperandStack stack = frame.operandStack();
        Object ref2 = stack.popRef();
        Object ref1 = stack.popRef();
        return ref1.equals(ref2);
    }

}
