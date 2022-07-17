package com.zqq.instructions.references;

import com.zqq.instructions.base.InstructionIndex16;
import com.zqq.runtimedata.Frame;
import com.zqq.runtimedata.OperandStack;
import com.zqq.runtimedata.heap.constantpoll.FieldRef;
import com.zqq.runtimedata.heap.constantpoll.RunTimeConstantPool;
import com.zqq.runtimedata.heap.methodarea.Class;
import com.zqq.runtimedata.heap.methodarea.Field;
import com.zqq.runtimedata.heap.methodarea.Method;
import com.zqq.runtimedata.heap.methodarea.Object;

/**
 * 给实例变量赋值,所赋的值保存在操作数栈中;
 * 和静态变量赋值不同的是:静态变量在class中,而实例变量在每一个对象中,该对象也在当前操作数栈;
 */
public class PUT_FIELD extends InstructionIndex16 {

    @Override
    public void execute(Frame frame) {
        Method currentMethod = frame.method();
        Class currentClazz = currentMethod.clazz();
        RunTimeConstantPool runTimeConstantPool = currentClazz.constantPool();
        //首先获取到fieldRef引用;
        FieldRef fieldRef = (FieldRef) runTimeConstantPool.getConstants(this.idx);
        //首先获取到fieldRef引用;
        Field field = fieldRef.resolvedField();
        //其实是可以通过实例访问类静态变量的，但这样无谓的增加了编译器解析的成本，因此这里直接抛出异常
        if (field.isStatic()) {
            throw new IncompatibleClassChangeError();
        }

        if (field.isFinal()) {
            if (currentClazz != field.clazz() || !"<init>".equals(currentMethod.name())){
                throw new IllegalAccessError();
            }
        }

        String descriptor = field.descriptor();
        int slotId = field.slotId();
        OperandStack stack = frame.operandStack();

        switch (descriptor.substring(0, 1)) {
            case "Z":
            case "B":
            case "C":
            case "S":
            case "I":
                int valInt = stack.popInt();
                Object refInt = stack.popRef();
                if (null == refInt) {
                    throw new NullPointerException();
                }
                refInt.fields().setInt(slotId, valInt);
                break;
            case "F":
                float valFloat = stack.popFloat();
                Object refFloat = stack.popRef();
                if (null == refFloat) {
                    throw new NullPointerException();
                }
                refFloat.fields().setFloat(slotId, valFloat);
                break;
            case "J":
                long valLong = stack.popLong();
                Object refLong = stack.popRef();
                if (null == refLong) {
                    throw new NullPointerException();
                }
                refLong.fields().setLong(slotId, valLong);
                break;
            case "D":
                double valDouble = stack.popDouble();
                Object refDouble = stack.popRef();
                if (null == refDouble) {
                    throw new NullPointerException();
                }
                refDouble.fields().setDouble(slotId, valDouble);
                break;
            case "L":
            case "[":
                Object val = stack.popRef();
                Object ref = stack.popRef();
                if (null == ref) {
                    throw new NullPointerException();
                }
                ref.fields().setRef(slotId, val);
                break;
            default:
                break;
        }
    }

}
