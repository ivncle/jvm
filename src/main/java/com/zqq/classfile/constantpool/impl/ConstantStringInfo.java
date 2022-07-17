package com.zqq.classfile.constantpool.impl;

import com.zqq.classfile.ClassReader;
import com.zqq.classfile.constantpool.ConstantInfo;
import com.zqq.classfile.constantpool.ConstantPool;

/**
 * 没有字符串，而是保存了一个常量池的索引，该索引指向的正是一个 ConstantUtf8Info 常量
 * CONSTANT_String_info {
 *  u1 tag;
 *  u2 string_index;
 * }
 */
public class ConstantStringInfo implements ConstantInfo {

    private ConstantPool constantPool;
    private int strIdx;

    public ConstantStringInfo(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    @Override
    public void readInfo(ClassReader reader) {
        this.strIdx = reader.readUint16();
    }

    @Override
    public int tag() {
        return this.CONSTANT_TAG_STRING;
    }

    public String string(){
        return this.constantPool.getUTF8(this.strIdx);
    }


}
