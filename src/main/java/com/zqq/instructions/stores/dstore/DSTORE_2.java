package com.zqq.instructions.stores.dstore;

import com.zqq.instructions.base.InstructionNoOperands;
import com.zqq.runtimedata.Frame;

public class DSTORE_2 extends InstructionNoOperands {

    @Override
    public void execute(Frame frame) {
        _dstore(frame, 2);
    }

}


