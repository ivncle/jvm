package com.zqq.classfile.constantpool.impl;

import com.zqq.classfile.ClassReader;
import com.zqq.classfile.constantpool.ConstantInfo;

/**
 * 表示方法句柄
 * CONSTANT_MethodHandle_info {
 *  u1 tag;
 *  类型(reference_kind项的值必须在1到9之间。这个值表示此方法句柄的类型，它表征其字节码行为)
 *  u1 reference_kind;
 *  u2 reference_index;
 * }
 */
public class ConstantMethodHandleInfo implements ConstantInfo {
    //决定后面referenceIndex是哪种类型的索引
    private int referenceKind;
    private int referenceIndex;

    @Override
    public void readInfo(ClassReader reader) {
        this.referenceKind = reader.readUint8();
        this.referenceIndex = reader.readUint16();
    }

    @Override
    public int tag() {
        return this.CONSTANT_TAG_METHODHANDLE;
    }
}
