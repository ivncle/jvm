package com.zqq.instructions.stores.istore;

import com.zqq.instructions.base.InstructionIndex8;
import com.zqq.runtimedata.Frame;

public class ISTORE extends InstructionIndex8 {

    @Override
    public void execute(Frame frame) {
        _istore(frame, this.idx);
    }

}
