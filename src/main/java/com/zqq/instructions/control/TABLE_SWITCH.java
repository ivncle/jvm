package com.zqq.instructions.control;

import com.zqq.instructions.base.BytecodeReader;
import com.zqq.instructions.base.Instruction;
import com.zqq.runtimedata.Frame;

/**
 * 索引连续
 */
//access jump table by index and jump
public class TABLE_SWITCH implements Instruction {

    private int defaultOffset;
    private int low;
    private int high;
    //jumpOffsets是一个索引表，里面存放high-low+1个int值,，对应各种case情况下，执行跳转所需的字节码偏移量
    private int[] jumpOffsets;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        //字节对齐
        reader.skipPadding();
        this.defaultOffset = reader.readInt();
        this.low = reader.readInt();
        this.high = reader.readInt();
        int jumpOffsetCount = this.high - this.low + 1;
        this.jumpOffsets = reader.readInts(jumpOffsetCount);
    }

    @Override
    public void execute(Frame frame) {
        int idx = frame.operandStack().popInt();
        int offset;
        if (idx >= this.low && idx <= this.high) {
            offset = this.jumpOffsets[idx - this.low];
        } else {
            offset = this.defaultOffset;
        }
        Instruction.branch(frame, offset);
    }

}
