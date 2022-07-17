package com.zqq.instructions.math.iinc;

import com.zqq.instructions.base.BytecodeReader;
import com.zqq.instructions.base.Instruction;
import com.zqq.runtimedata.Frame;
import com.zqq.runtimedata.LocalVars;

/**
 * iinc指令给局部变量表中的int变量增加常量值，局部变量表索引和常量值都由指令的操作数提供
 */
//increment local variable by constants
public class IINC implements Instruction {

    public int idx;
    public int constVal;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        this.idx = reader.readByte();
        this.constVal = reader.readByte();
    }

    @Override
    public void execute(Frame frame) {
        LocalVars vars = frame.localVars();
        int val = vars.getInt(this.idx);
        val += this.constVal;
        vars.setInt(this.idx, val);
    }
}

