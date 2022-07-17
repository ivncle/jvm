package com.zqq.instructions.references;

import com.zqq.instructions.base.InstructionIndex16;
import com.zqq.runtimedata.Frame;
import com.zqq.runtimedata.OperandStack;
import com.zqq.runtimedata.heap.constantpoll.ClassRef;
import com.zqq.runtimedata.heap.constantpoll.RunTimeConstantPool;
import com.zqq.runtimedata.heap.methodarea.Class;
import com.zqq.runtimedata.heap.methodarea.Object;

/**
 * 创建引用类型数组
 * 如果创建的是 String[],那么从运行时常量池拿到 String 的符号引用，通过符号引用将 String 类加载进来
 * 接下来再将 String[] 类加载进来！
 * 最后通过 String[] 类创建 字符串数组对象，并压入操作数栈
 */
public class ANEW_ARRAY extends InstructionIndex16 {

    @Override
    public void execute(Frame frame) {

        RunTimeConstantPool runTimeConstantPool = frame.method().clazz().constantPool();
        //首先获取到该数组的引用类型
        //eg:String [] arr = new String[2];  那么获取到的类是:java.lang.String
        ClassRef classRef = (ClassRef) runTimeConstantPool.getConstants(this.idx);
        //该引用类型若没加载过,那么先将该引用类型加载进来
        Class componentClass = classRef.resolvedClass();

        OperandStack stack = frame.operandStack();
        int count = stack.popInt();
        if (count < 0) {
            throw new NegativeArraySizeException();
        }
        //根据基础类型:java/lang/String,得到数组类[Ljava/lang/String
        Class arrClass = componentClass.arrayClass();
        //根据数组类去创建具体的数组对象;
        Object arr = arrClass.newArray(count);
        stack.pushRef(arr);

    }

}
