package com.zqq.instructions.extended;

import com.zqq.instructions.base.BytecodeReader;
import com.zqq.instructions.base.Instruction;
import com.zqq.instructions.loads.aload.ALOAD;
import com.zqq.instructions.loads.dload.DLOAD;
import com.zqq.instructions.loads.fload.FLOAD;
import com.zqq.instructions.loads.iload.ILOAD;
import com.zqq.instructions.loads.lload.LLOAD;
import com.zqq.instructions.math.iinc.IINC;
import com.zqq.instructions.stores.astore.ASTORE;
import com.zqq.instructions.stores.dstore.DSTORE;
import com.zqq.instructions.stores.fstore.FSTORE;
import com.zqq.instructions.stores.istore.ISTORE;
import com.zqq.instructions.stores.lstore.LSTORE;
import com.zqq.runtimedata.Frame;

/**
 * 加载类指令、存储类指令、ret 指令和 iinc 指令需要按索引访问局部变量表，索引以 uint8 的形式存在字节码中。
 * 对于大部分方法来说，局部变量表大小一般都不会超过 256，所以用一字节来表示索引就够了。
 * 但是如果有方法的局部变量表超过这限制时,Java 虚拟机规范定义了 wide 指令来扩展前述指令
 */
public class WIDE implements Instruction {

    private Instruction modifiedInstruction;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        byte opcode = reader.readByte();
        switch (opcode){
            case 0x15:
                ILOAD inst_iload = new ILOAD();
                inst_iload.idx = reader.readShort();
                this.modifiedInstruction = inst_iload;
            case 0x16:
                LLOAD inst_lload = new LLOAD();
                inst_lload.idx = reader.readShort();
                this.modifiedInstruction = inst_lload;
            case 0x17:
                FLOAD inst_fload = new FLOAD();
                inst_fload.idx = reader.readShort();
                this.modifiedInstruction = inst_fload;
            case 0x18:
                DLOAD inst_dload = new DLOAD();
                inst_dload.idx = reader.readShort();
                this.modifiedInstruction = inst_dload;
            case 0x19:
                ALOAD inst_aload = new ALOAD();
                inst_aload.idx = reader.readShort();
                this.modifiedInstruction = inst_aload;
            case 0x36:
                ISTORE inst_istore = new ISTORE();
                inst_istore.idx = reader.readShort();
                this.modifiedInstruction = inst_istore;
            case 0x37:
                LSTORE inst_lstore = new LSTORE();
                inst_lstore.idx = reader.readShort();
                this.modifiedInstruction = inst_lstore;
            case 0x38:
                FSTORE inst_fstore = new FSTORE();
                inst_fstore.idx = reader.readShort();
                this.modifiedInstruction = inst_fstore;
            case 0x39:
                DSTORE inst_dstore = new DSTORE();
                inst_dstore.idx = reader.readShort();
                this.modifiedInstruction = inst_dstore;
            case 0x3a:
                ASTORE inst_astore = new ASTORE();
                inst_astore.idx = reader.readShort();
                this.modifiedInstruction = inst_astore;
            case (byte) 0x84:
                IINC inst_iinc = new IINC();
                inst_iinc.idx = reader.readShort();
                this.modifiedInstruction = inst_iinc;
            case (byte) 0xa9: // ret
                throw new RuntimeException("Unsupported opcode: 0xa9!");
        }
    }

    @Override
    public void execute(Frame frame) {
        this.modifiedInstruction.execute(frame);
    }
}
