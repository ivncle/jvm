package com.zqq.instructions.references;

import com.zqq.instructions.base.InstructionIndex16;
import com.zqq.runtimedata.Frame;
import com.zqq.runtimedata.OperandStack;
import com.zqq.runtimedata.heap.constantpoll.ClassRef;
import com.zqq.runtimedata.heap.constantpoll.RunTimeConstantPool;
import com.zqq.runtimedata.heap.methodarea.Class;
import com.zqq.runtimedata.heap.methodarea.Object;

/**
 * 类型转换,该指令和instanceof指令的区别在于,
 * instanceof判断后将结果压入操作数栈,而cast直接抛出异常
 * 在pop到引用 obj 之后，又将 obj push 到栈中
 */
public class CHECK_CAST extends InstructionIndex16 {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack();
        Object ref = stack.popRef();
        stack.pushRef(ref);
        if (null == ref) return;

        RunTimeConstantPool cp = frame.method().clazz().constantPool();
        ClassRef clazzRef = (ClassRef) cp.getConstants(this.idx);
        Class clazz = clazzRef.resolvedClass();

        if (!ref.isInstanceOf(clazz)) {
            throw new ClassCastException();
        }
    }

}
