package com.zqq.classfile.constantpool.impl;

import com.zqq.classfile.ClassReader;
import com.zqq.classfile.constantpool.ConstantInfo;
import com.zqq.classfile.constantpool.ConstantPool;

/**
 * 用于表示一个类或者接口
 * CONSTANT_Class_info {
 *  u1 tag;
 *  u2 name_index;
 * }
 */
public class ConstantClassInfo implements ConstantInfo {

    public ConstantPool constantPool;
    //表示类的符号引用索引
    public int nameIdx;

    public ConstantClassInfo(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    @Override
    public void readInfo(ClassReader reader) {
        this.nameIdx = reader.readUint16();
    }

    public String name(){
        return this.constantPool.getUTF8(this.nameIdx);
    }

    @Override
    public int tag() {
        return this.CONSTANT_TAG_CLASS;
    }

}
