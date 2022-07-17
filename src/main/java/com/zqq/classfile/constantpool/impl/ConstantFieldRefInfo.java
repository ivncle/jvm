package com.zqq.classfile.constantpool.impl;

import com.zqq.classfile.constantpool.ConstantPool;

/**
 * 用来表示属性
 * CONSTANT_Fieldref_info {
 *  u1 tag;
 *  u2 class_index;
 *  u2 name_and_type_index;
 * }
 */
public class ConstantFieldRefInfo extends ConstantMemberRefInfo {

    public ConstantFieldRefInfo(ConstantPool constantPool) {
        super(constantPool);
    }

    @Override
    public int tag() {
        return this.CONSTANT_TAG_FIELDREF;
    }

}
