package com.zqq.instructions.control;

import com.zqq.instructions.base.BytecodeReader;
import com.zqq.instructions.base.Instruction;
import com.zqq.runtimedata.Frame;

/**
 * 索引非连续
 */
//access jump table by key match and jump
public class LOOKUP_SWITCH implements Instruction {

    private int defaultOffset;
    private int npairs;
    //matchOffsets有点像Map，它的key是case值，value是跳转偏移,但是并没有实现成map,而是用数组代替,两个连续的数位key-value;
    private int[] matchOffsets;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        //四字节对齐
        reader.skipPadding();
        this.defaultOffset = reader.readInt();
        this.npairs = reader.readInt();
        this.matchOffsets = reader.readInts(this.npairs * 2);
    }

    @Override
    public void execute(Frame frame) {
        int key = frame.operandStack().popInt();
        for (int i = 0; i < this.npairs * 2; i += 2) {
            if (this.matchOffsets[i] == key) {
                int offset = this.matchOffsets[i + 1];
                Instruction.branch(frame, offset);
            }
        }
        Instruction.branch(frame, this.defaultOffset);
    }
}
