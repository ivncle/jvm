package com.zqq.instructions.comparisons.dcmp;

import com.zqq.instructions.base.InstructionNoOperands;
import com.zqq.runtimedata.Frame;

/**
 * 将比较结果推入操作数栈顶
 * 根据比较结果跳转
 */
public class DCMPG extends InstructionNoOperands {

    @Override
    public void execute(Frame frame) {
        _dcmp(frame, true);
    }

}
