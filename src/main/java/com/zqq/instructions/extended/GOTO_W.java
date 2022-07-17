package com.zqq.instructions.extended;

import com.zqq.instructions.base.BytecodeReader;
import com.zqq.instructions.base.Instruction;
import com.zqq.runtimedata.Frame;

/**
 * goto_w指令和goto指令的唯一区别就是索引从2字节变成了4字节
 */
//branch always(wide index)
public class GOTO_W implements Instruction {

    private int offset;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        this.offset = reader.readInt();
    }

    @Override
    public void execute(Frame frame) {
        Instruction.branch(frame, this.offset);
    }

}
