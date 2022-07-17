package com.zqq.instructions.constants.ldc;

import com.zqq.instructions.base.InstructionIndex8;
import com.zqq.runtimedata.Frame;
import com.zqq.runtimedata.OperandStack;
import com.zqq.runtimedata.heap.constantpoll.ClassRef;
import com.zqq.runtimedata.heap.constantpoll.RunTimeConstantPool;
import com.zqq.runtimedata.heap.methodarea.Class;
import com.zqq.runtimedata.heap.methodarea.Object;
import com.zqq.runtimedata.heap.methodarea.StringPool;

/**
 * 该系列命令负责把数值常量或String常量值从常量池中推送至栈顶。
 * 该命令后面 需要给一个表示常量在常量池中位置(编号)的参数
 */
public class LDC extends InstructionIndex8 {

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
            return;
        }

        if (c instanceof ClassRef){
            ClassRef classRef = (ClassRef) c;
            Object classObj = classRef.resolvedClass().jClass();
            stack.pushRef(classObj);
            return;
        }

        throw new RuntimeException("todo ldc");
    }

}
