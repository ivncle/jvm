package com.zqq.instructions.constants.ldc;

import com.zqq.instructions.base.InstructionIndex16;
import com.zqq.runtimedata.Frame;
import com.zqq.runtimedata.OperandStack;
import com.zqq.runtimedata.heap.constantpoll.RunTimeConstantPool;

/**
 * LDC2_W 和 LDC 的区别是，其获取常量池的常量类型为 Long 和 Double，索引宽度不一样
 */
public class LDC2_W extends InstructionIndex16 {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack();
        RunTimeConstantPool runTimeConstantPool = frame.method().clazz().constantPool();
        Object c = runTimeConstantPool.getConstants(this.idx);
        if (c instanceof Long) {
            stack.pushLong((Long) c);
            return;
        }
        if (c instanceof Double){
            stack.pushDouble((Double) c);
        }
        throw new ClassFormatError();

    }

}
