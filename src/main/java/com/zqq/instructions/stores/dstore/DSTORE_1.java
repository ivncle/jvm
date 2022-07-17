package com.zqq.instructions.stores.dstore;

import com.zqq.instructions.base.InstructionNoOperands;
import com.zqq.runtimedata.Frame;

public class DSTORE_1 extends InstructionNoOperands {

    @Override
    public void execute(Frame frame) {
        _dstore(frame, 1);
    }

}
