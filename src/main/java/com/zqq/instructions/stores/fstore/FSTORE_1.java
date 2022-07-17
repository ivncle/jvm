package com.zqq.instructions.stores.fstore;

import com.zqq.instructions.base.InstructionNoOperands;
import com.zqq.runtimedata.Frame;

public class FSTORE_1 extends InstructionNoOperands {

    @Override
    public void execute(Frame frame) {
        _fstore(frame, 1);
    }

}

