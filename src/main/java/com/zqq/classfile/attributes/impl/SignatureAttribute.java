package com.zqq.classfile.attributes.impl;

import com.zqq.classfile.ClassReader;
import com.zqq.classfile.attributes.AttributeInfo;
import com.zqq.classfile.constantpool.ConstantPool;

/**
 * 记录字段、方法、和类的泛型信息
 */
public class SignatureAttribute implements AttributeInfo {

    private ConstantPool constantPool;
    private int signatureIdx;

    public SignatureAttribute(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    @Override
    public void readInfo(ClassReader reader) {
        this.signatureIdx = reader.readUint16();
    }

    public String signature(){
        return this.constantPool.getUTF8(this.signatureIdx);
    }

}
