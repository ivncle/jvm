package com.zqq.instructions.stores.istore;

import com.zqq.instructions.base.InstructionNoOperands;
import com.zqq.runtimedata.Frame;

public class ISTORE_1 extends InstructionNoOperands {

    @Override
    public void execute(Frame frame) {
        _istore(frame, 1);
    }

}
