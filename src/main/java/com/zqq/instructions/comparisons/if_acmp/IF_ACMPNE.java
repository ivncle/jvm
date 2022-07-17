package com.zqq.instructions.comparisons.if_acmp;

import com.zqq.instructions.base.Instruction;
import com.zqq.instructions.base.InstructionBranch;
import com.zqq.runtimedata.Frame;

public class IF_ACMPNE extends InstructionBranch {

    @Override
    public void execute(Frame frame) {
        if (!_acmp(frame)) {
            Instruction.branch(frame, this.offset);
        }
    }

}
