package com.zqq.instructions.references;

import com.zqq.instructions.base.ClassInitLogic;
import com.zqq.instructions.base.InstructionIndex16;
import com.zqq.runtimedata.Frame;
import com.zqq.runtimedata.OperandStack;
import com.zqq.runtimedata.heap.constantpoll.FieldRef;
import com.zqq.runtimedata.heap.constantpoll.RunTimeConstantPool;
import com.zqq.runtimedata.heap.methodarea.Class;
import com.zqq.runtimedata.heap.methodarea.Field;
import com.zqq.runtimedata.heap.methodarea.Slots;

/**
 * 获取静态变量的值，将其值放在操作数栈中
 */
public class GET_STATIC extends InstructionIndex16 {

    @Override
    public void execute(Frame frame) {
        RunTimeConstantPool runTimeConstantPool = frame.method().clazz().constantPool();
        FieldRef ref = (FieldRef) runTimeConstantPool.getConstants(this.idx);
        Field field = ref.resolvedField();
        Class clazz = field.clazz();
        //判断其Class是否已经加载过,如果还未加载,那么调用其类的<clinit>方法压栈
        if (!clazz.initStarted()) {
            frame.revertNextPC();
            ClassInitLogic.initClass(frame.thread(), clazz);
            return;
        }
        //非静态变量
        if (!field.isStatic()) {
            throw new IncompatibleClassChangeError();
        }

        String descriptor = field.descriptor();
        int slotId = field.slotId();
        Slots slots = clazz.staticVars();
        OperandStack stack = frame.operandStack();

        switch (descriptor.substring(0, 1)) {
            case "Z":
            case "B":
            case "C":
            case "S":
            case "I":
                stack.pushInt(slots.getInt(slotId));
                break;
            case "F":
                stack.pushFloat(slots.getFloat(slotId));
                break;
            case "J":
                stack.pushLong(slots.getLong(slotId));
                break;
            case "D":
                stack.pushDouble(slots.getDouble(slotId));
                break;
            case "L":
            case "[":
                stack.pushRef(slots.getRef(slotId));
                break;
            default:
                break;
        }
    }

}
