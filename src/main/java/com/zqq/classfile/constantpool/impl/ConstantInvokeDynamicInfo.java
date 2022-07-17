package com.zqq.classfile.constantpool.impl;

import com.zqq.classfile.ClassReader;
import com.zqq.classfile.constantpool.ConstantInfo;

/**
 * 指定引导方法，动态调用名称、调用的参数和返回类型
 * CONSTANT_InvokeDynamic_info {
 *  u1 tag;
 *  u2 bootstrap_method_attr_index;
 *  u2 name_and_type_index;
 * }
 */
public class ConstantInvokeDynamicInfo implements ConstantInfo {
    //引导方法的有效索引法记录在
    private int bootstrapMethodAttrIdx;
    //方法名称和描述符索引
    private int nameAndTypeIdx;

    @Override
    public void readInfo(ClassReader reader) {
        this.bootstrapMethodAttrIdx = reader.readUint16();
        this.nameAndTypeIdx = reader.readUint16();
    }

    @Override
    public int tag() {
        return this.CONSTANT_TAG_INVOKEDYNAMIC;
    }
}
