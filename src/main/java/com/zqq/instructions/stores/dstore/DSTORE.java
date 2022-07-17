package com.zqq.instructions.stores.dstore;

import com.zqq.instructions.base.InstructionIndex8;
import com.zqq.runtimedata.Frame;

public class DSTORE extends InstructionIndex8 {

    @Override
    public void execute(Frame frame) {
        _astore(frame, this.idx);
    }

}
