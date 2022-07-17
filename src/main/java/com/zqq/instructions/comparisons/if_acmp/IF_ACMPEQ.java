package com.zqq.instructions.comparisons.if_acmp;

import com.zqq.instructions.base.Instruction;
import com.zqq.instructions.base.InstructionBranch;
import com.zqq.runtimedata.Frame;

/**
 * 两个对象相等则跳转
 */
public class IF_ACMPEQ extends InstructionBranch {

    @Override
    public void execute(Frame frame) {
        if (_acmp(frame)) {
            Instruction.branch(frame, this.offset);
        }
    }

}
