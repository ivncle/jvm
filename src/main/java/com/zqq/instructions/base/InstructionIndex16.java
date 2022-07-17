package com.zqq.instructions.base;

import com.zqq.runtimedata.Frame;

/**
 * 有一些指令需要访问运行时常量池，常量池索引由两字节操作数给出。
 * 把这类指令抽象成 Index16Instruction 结构体，用 Index 字段表示常量池索引。
 * FetchOperands（）方法从字节码中读取一个 int16 整数，转成 int 后赋给 Index 字段。
 */
public class InstructionIndex16 implements Instruction {

    protected int idx;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        this.idx = reader.readShort();
    }

    @Override
    public void execute(Frame frame) {

    }

}
