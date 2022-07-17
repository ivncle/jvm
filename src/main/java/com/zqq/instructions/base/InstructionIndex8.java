package com.zqq.instructions.base;

import com.zqq.runtimedata.Frame;
import com.zqq.runtimedata.heap.methodarea.Object;

/**
 * 存储和加载类指令需要根据索引存取局部变量表，索引由单字节操作数给出。
 * 把这类指令抽象成 Index8Instruction ，用 Index 字段表示局部变量表索引。
 * FetchOperands（）方法从字节码中读取一个 int8 整数赋给 Index 字段。
 */
public class InstructionIndex8 implements Instruction {

    public int idx;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        this.idx = reader.readByte();
    }

    @Override
    public void execute(Frame frame) {

    }

    protected void _astore(Frame frame, int idx) {
        Object ref = frame.operandStack().popRef();
        frame.localVars().setRef(idx, ref);
    }

    protected void _dstore(Frame frame, int idx) {
        double val = frame.operandStack().popDouble();
        frame.localVars().setDouble(idx, val);
    }

    protected void _fstore(Frame frame, int idx) {
        float val = frame.operandStack().popFloat();
        frame.localVars().setFloat(idx, val);
    }

    protected void _istore(Frame frame, int idx) {
        int val = frame.operandStack().popInt();
        frame.localVars().setInt(idx, val);
    }

    protected void _lstore(Frame frame, int idx) {
        long val = frame.operandStack().popLong();
        frame.localVars().setLong(idx, val);
    }

}
