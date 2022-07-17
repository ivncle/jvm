package com.zqq.classfile.attributes.impl;

import com.zqq.classfile.ClassReader;
import com.zqq.classfile.attributes.AttributeInfo;
import com.zqq.classfile.constantpool.ConstantPool;

import java.util.HashMap;
import java.util.Map;

/**
 * 匿名类或者局部类（如同局部变量一样在方法内部声明的类）
 */
public class EnclosingMethodAttribute implements AttributeInfo {

    private ConstantPool constantPool;
    //外部类引用指向ConstantClassInfo
    private int classIdx;
    //方法引用指向ConstantNameAndtypeInfo
    private int methodIdx;


    public EnclosingMethodAttribute(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    @Override
    public void readInfo(ClassReader reader) {
        this.classIdx = reader.readUint16();
        this.methodIdx = reader.readUint16();
    }

    public String className() {
        return this.constantPool.getClassName(this.classIdx);
    }

    public Map<String, String> methodNameAndDescriptor() {
        if (this.methodIdx <= 0) return new HashMap<>();
        return this.constantPool.getNameAndType(this.methodIdx);
    }

}
