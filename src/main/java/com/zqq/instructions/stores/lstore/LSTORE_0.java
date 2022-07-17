package com.zqq.instructions.stores.lstore;

import com.zqq.instructions.base.InstructionNoOperands;
import com.zqq.runtimedata.Frame;

public class LSTORE_0 extends InstructionNoOperands {

    @Override
    public void execute(Frame frame) {
        _lstore(frame, 0);
    }

}
