package com.zqq.instructions.stores.astore;

import com.zqq.instructions.base.InstructionNoOperands;
import com.zqq.runtimedata.Frame;

public class ASTORE_1 extends InstructionNoOperands {

    @Override
    public void execute(Frame frame) {
        _astore(frame, 1);
    }

}
