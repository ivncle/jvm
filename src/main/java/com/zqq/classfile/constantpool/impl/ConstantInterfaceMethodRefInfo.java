package com.zqq.classfile.constantpool.impl;

import com.zqq.classfile.constantpool.ConstantPool;

/**
 * 接口方法符号引用
 * CONSTANT_InterfaceMethodref_info {
 *  u1 tag;
 *  u2 class_index;
 *  u2 name_and_type_index;
 * }
 */
public class ConstantInterfaceMethodRefInfo extends ConstantMemberRefInfo {

    public ConstantInterfaceMethodRefInfo(ConstantPool constantPool) {
        super(constantPool);
    }

    @Override
    public int tag() {
        return this.CONSTANT_TAG_INTERFACEMETHODREF;
    }

}
