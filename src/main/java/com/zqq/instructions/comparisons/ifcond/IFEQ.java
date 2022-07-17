package com.zqq.instructions.comparisons.ifcond;

import com.zqq.instructions.base.Instruction;
import com.zqq.instructions.base.InstructionBranch;
import com.zqq.runtimedata.Frame;

/**
 * ifeq：x==0
 * ifne：x！=0
 * iflt：x<0
 * ifle：x<=0
 * ifgt：x>0
 * ifge：x>=0
 */
public class IFEQ extends InstructionBranch {

    @Override
    public void execute(Frame frame) {
        int val = frame.operandStack().popInt();
        if (0 == val) {
            Instruction.branch(frame, this.offset);
        }
    }
}
