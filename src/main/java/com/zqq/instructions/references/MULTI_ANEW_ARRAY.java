package com.zqq.instructions.references;

import com.zqq.instructions.base.BytecodeReader;
import com.zqq.instructions.base.Instruction;
import com.zqq.runtimedata.Frame;
import com.zqq.runtimedata.OperandStack;
import com.zqq.runtimedata.heap.constantpoll.ClassRef;
import com.zqq.runtimedata.heap.constantpoll.RunTimeConstantPool;
import com.zqq.runtimedata.heap.methodarea.Class;
import com.zqq.runtimedata.heap.methodarea.Object;

public class MULTI_ANEW_ARRAY implements Instruction {

    private short idx;
    //数组的维度
    private byte dimensions;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        this.idx = (short) reader.readShort();
        this.dimensions = reader.readByte();
    }

    @Override
    public void execute(Frame frame) {
        RunTimeConstantPool runTimeConstantPool = frame.method().clazz().constantPool();
        //多维数组的类符号引用
        ClassRef classRef = (ClassRef) runTimeConstantPool.getConstants((int) this.idx);
        //解析该数组引用，就能得到该多维数组的类
        Class arrClass = classRef.resolvedClass();

        OperandStack stack = frame.operandStack();
        //各个维的大小;
        int[] counts = popAndCheckCounts(stack, this.dimensions);
        Object arr = newMultiDimensionalArray(counts, arrClass);
        stack.pushRef(arr);

    }
    //从操作数栈中弹出dimensions个整数,分别表示各个维度的大小
    private int[] popAndCheckCounts(OperandStack stack, int dimensions) {
        int[] counts = new int[dimensions];
        for (int i = dimensions - 1; i >= 0; i--) {
            counts[i] = stack.popInt();
            if (counts[i] < 0) {
                throw new NegativeArraySizeException();
            }
        }

        return counts;
    }
    //创建多维数组
    private Object newMultiDimensionalArray(int[] counts, Class arrClass) {//2  3
        int count = counts[0];
        //首先创建第一行
        Object arr = arrClass.newArray(count);
        if (counts.length > 1) {
            Object[] refs = arr.refs();
            for (int i = 0; i < refs.length; i++) {
                //在创建新的数组，传入的 class 是上面一层 class 的名称，退去了一层 [
                int[] copyCount = new int[counts.length - 1];
                System.arraycopy(counts, 1, copyCount, 0, counts.length - 1);
                refs[i] = newMultiDimensionalArray(copyCount, arrClass.componentClass());
            }
        }

        return arr;
    }

}
