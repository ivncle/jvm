package com.zqq.instructions.comparisons.ifcond;

import com.zqq.instructions.base.Instruction;
import com.zqq.instructions.base.InstructionBranch;
import com.zqq.runtimedata.Frame;

public class IFNE extends InstructionBranch {

    @Override
    public void execute(Frame frame) {
        int val = frame.operandStack().popInt();
        if (val != 0) {
            Instruction.branch(frame, this.offset);
        }
    }
}
