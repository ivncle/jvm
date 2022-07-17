package com.zqq.instructions.references;

import com.zqq.instructions.base.BytecodeReader;
import com.zqq.instructions.base.Instruction;
import com.zqq.runtimedata.Frame;
import com.zqq.runtimedata.OperandStack;
import com.zqq.runtimedata.heap.ClassLoader;
import com.zqq.runtimedata.heap.methodarea.Class;
import com.zqq.runtimedata.heap.methodarea.Object;

/**
 * 创建基本类型的数组的指令
 */
public class NEW_ARRAY implements Instruction {

    private byte atype;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        this.atype = reader.readByte();
    }

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.operandStack();
        int count = stack.popInt();
        if (count < 0) {
            throw new NegativeArraySizeException();
        }

        ClassLoader classLoader = frame.method().clazz().loader();
        Class arrClass = getPrimitiveArrayClass(classLoader, this.atype);
        Object arr = arrClass.newArray(count);
        stack.pushRef(arr);

    }
    //获取基本类型数组的class;如果没有加载过,需要加载进JVM
    private Class getPrimitiveArrayClass(ClassLoader loader, byte atype){
        switch (atype) {
            case ArrayType.AT_BOOLEAN:
                return loader.loadClass("[Z");
            case ArrayType.AT_BYTE:
                return loader.loadClass("[B");
            case ArrayType.AT_CHAR:
                return loader.loadClass("[C");
            case ArrayType.AT_SHORT:
                return loader.loadClass("[S");
            case ArrayType.AT_INT:
                return loader.loadClass("[I");
            case ArrayType.AT_LONG:
                return loader.loadClass("[J");
            case ArrayType.AT_FLOAT:
                return loader.loadClass("[F");
            case ArrayType.AT_DOUBLE:
                return loader.loadClass("[D");
            default:
                throw new RuntimeException("Invalid atype!");
        }
    }

    static class ArrayType {
        static final byte AT_BOOLEAN = 4;
        static final byte AT_CHAR = 5;
        static final byte AT_FLOAT = 6;
        static final byte AT_DOUBLE = 7;
        static final byte AT_BYTE = 8;
        static final byte AT_SHORT = 9;
        static final byte AT_INT = 10;
        static final byte AT_LONG = 11;
    }

}
