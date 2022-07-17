package com.zqq.instructions.stores.lstore;

import com.zqq.instructions.base.InstructionIndex8;
import com.zqq.runtimedata.Frame;

public class LSTORE extends InstructionIndex8 {

    @Override
    public void execute(Frame frame) {
        _lstore(frame, this.idx);
    }

}
