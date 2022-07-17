package com.zqq.instructions.constants.ldc;

import com.zqq.instructions.base.InstructionIndex16;
import com.zqq.runtimedata.Frame;
import com.zqq.runtimedata.OperandStack;
import com.zqq.runtimedata.heap.constantpoll.RunTimeConstantPool;
import com.zqq.runtimedata.heap.methodarea.Class;
import com.zqq.runtimedata.heap.methodarea.Object;
import com.zqq.runtimedata.heap.methodarea.StringPool;

/**
 *LDC_W和 LDC 的 execute 是完全一样的，唯一的区别就是index位宽，w 取16位，非 w 取8位
 */
public class LDC_W extends InstructionIndex16 {

    @Override
    public void execute(Frame frame) {
        _ldc(frame, this.idx);
    }

    private void _ldc(Frame frame, int idx) {
        OperandStack stack = frame.operandStack();
        Class clazz = frame.method().clazz();
        RunTimeConstantPool runTimeConstantPool = frame.method().clazz().constantPool();
        java.lang.Object c = runTimeConstantPool.getConstants(idx);

        if (c instanceof Integer) {
            stack.pushInt((Integer) c);
            return;
        }

        if (c instanceof Float) {
            stack.pushFloat((Float) c);
            return;
        }

        if (c instanceof String) {
            Object internedStr = StringPool.jString(clazz.loader(), (String) c);
            stack.pushRef(internedStr);
        }

        throw new RuntimeException("todo ldc");
    }

}
