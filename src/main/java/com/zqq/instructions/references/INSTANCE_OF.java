package com.zqq.instructions.references;

import com.zqq.instructions.base.InstructionIndex16;
import com.zqq.runtimedata.Frame;
import com.zqq.runtimedata.OperandStack;
import com.zqq.runtimedata.heap.constantpoll.ClassRef;
import com.zqq.runtimedata.heap.constantpoll.RunTimeConstantPool;
import com.zqq.runtimedata.heap.methodarea.Class;
import com.zqq.runtimedata.heap.methodarea.Object;

/**
 * java 中的 instanceof 关键字，会被解析成 INSTANCE_OF 指令 eg:(obj instanceof Object)
 * 将判断的结果写入操作数栈
 */
public class INSTANCE_OF extends InstructionIndex16 {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack();
        Object ref = stack.popRef();
        if (null == ref){
            stack.pushInt(0);
            return;
        }
        RunTimeConstantPool cp = frame.method().clazz().constantPool();
        ClassRef classRef = (ClassRef) cp.getConstants(this.idx);
        Class clazz = classRef.resolvedClass();
        if (ref.isInstanceOf(clazz)){
            stack.pushInt(1);
        } else {
            stack.pushInt(0);
        }
    }

}
