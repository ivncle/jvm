package com.zqq.instructions.extended.ifnull;

import com.zqq.instructions.base.Instruction;
import com.zqq.instructions.base.InstructionBranch;
import com.zqq.runtimedata.Frame;

//branch if reference is null
public class IFNULL extends InstructionBranch {

    @Override
    public void execute(Frame frame) {
        Object ref = frame.operandStack().popRef();
        if (null == ref) {
            Instruction.branch(frame, this.offset);
        }
    }
}
