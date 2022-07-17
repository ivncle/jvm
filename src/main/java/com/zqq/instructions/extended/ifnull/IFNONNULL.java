package com.zqq.instructions.extended.ifnull;

import com.zqq.instructions.base.Instruction;
import com.zqq.instructions.base.InstructionBranch;
import com.zqq.runtimedata.Frame;

/**
 * 引用是否是 null 进行跳转，ifnull和ifnonnull指令把栈顶的引用弹出
 */
public class IFNONNULL extends InstructionBranch {

    @Override
    public void execute(Frame frame) {
        Object ref = frame.operandStack().popRef();
        if (null != ref) {
            Instruction.branch(frame, this.offset);
        }
    }
}