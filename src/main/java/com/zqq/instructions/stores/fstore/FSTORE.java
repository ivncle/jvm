package com.zqq.instructions.stores.fstore;

import com.zqq.instructions.base.InstructionIndex8;
import com.zqq.runtimedata.Frame;

public class FSTORE extends InstructionIndex8 {

    @Override
    public void execute(Frame frame) {
        _fstore(frame, this.idx);
    }

}
