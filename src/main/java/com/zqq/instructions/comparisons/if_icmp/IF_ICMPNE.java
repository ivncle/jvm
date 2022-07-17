package com.zqq.instructions.comparisons.if_icmp;

import com.zqq.instructions.base.Instruction;
import com.zqq.instructions.base.InstructionBranch;
import com.zqq.runtimedata.Frame;
import com.zqq.runtimedata.OperandStack;

public class IF_ICMPNE extends InstructionBranch {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack();
        int val2 = stack.popInt();
        int val1 = stack.popInt();
        if (val1 != val2) {
            Instruction.branch(frame, this.offset);
        }
    }

}