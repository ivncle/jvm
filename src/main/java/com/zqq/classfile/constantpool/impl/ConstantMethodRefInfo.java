package com.zqq.classfile.constantpool.impl;

import com.zqq.classfile.constantpool.ConstantPool;

/**
 * 方法引用信息
 * CONSTANT_Methodref_info {
 *  u1 tag;
 *  u2 class_index;
 *  u2 name_and_type_index;
 * }
 */
public class ConstantMethodRefInfo extends ConstantMemberRefInfo {

    public ConstantMethodRefInfo(ConstantPool constantPool) {
        super(constantPool);
    }

    @Override
    public int tag() {
        return this.CONSTANT_TAG_METHODREF;
    }
}
