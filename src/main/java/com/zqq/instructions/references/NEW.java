package com.zqq.instructions.references;

import com.zqq.instructions.base.ClassInitLogic;
import com.zqq.instructions.base.InstructionIndex16;
import com.zqq.runtimedata.Frame;
import com.zqq.runtimedata.heap.constantpoll.ClassRef;
import com.zqq.runtimedata.heap.constantpoll.RunTimeConstantPool;
import com.zqq.runtimedata.heap.methodarea.Class;
import com.zqq.runtimedata.heap.methodarea.Object;

/**
 * uint16的索引,来自字节码,通过该索引,从当前类的运行时常量池中找到类符号引用;
 * 解析该类符号引用,可以拿到类数据,然后创建对象,并把对象引用压入操作数栈;
 */
public class NEW extends InstructionIndex16 {

    @Override
    public void execute(Frame frame) {
        //判断其Class是否已经加载过,如果还未加载,那么调用其类的<clinit>方法压栈
        RunTimeConstantPool cp = frame.method().clazz().constantPool();
        ClassRef classRef = (ClassRef) cp.getConstants(this.idx);
        Class clazz = classRef.resolvedClass();
        if (!clazz.initStarted()) {
            //当前指令已经是在执行new了,但是类还没有加载,所以当前帧先回退,让类初始化的帧入栈,等类初始化完成后,重新执行new;
            frame.revertNextPC();
            ClassInitLogic.initClass(frame.thread(), clazz);
            return;
        }

        if (clazz.isInterface() || clazz.isAbstract()) {
            throw new InstantiationError();
        }

        Object ref = clazz.newObject();
        frame.operandStack().pushRef(ref);
    }

}