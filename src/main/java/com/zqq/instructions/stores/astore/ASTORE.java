package com.zqq.instructions.stores.astore;

import com.zqq.instructions.base.InstructionIndex8;
import com.zqq.runtimedata.Frame;

/**
 * 把变量从操作数栈顶弹出，然后存入局部变量表
 */
public class ASTORE extends InstructionIndex8 {

    @Override
    public void execute(Frame frame) {
        _astore(frame, this.idx);
    }

}

