package com.zqq.classfile.attributes.impl;

import com.zqq.classfile.ClassReader;
import com.zqq.classfile.attributes.AttributeInfo;

/**
 * 方法中局部变量描述
 */
public class LocalVariableTableAttribute implements AttributeInfo {

    private LocalVariableTableEntry[] localVariableTable;

    @Override
    public void readInfo(ClassReader reader) {
        int localVariableTableLength = reader.readUint16();
        this.localVariableTable = new LocalVariableTableEntry[localVariableTableLength];
        for (int i = 0; i < localVariableTableLength; i++) {
            this.localVariableTable[i] = new LocalVariableTableEntry(reader.readUint16(), reader.readUint16(), reader.readUint16(), reader.readUint16(), reader.readUint16());
        }
    }

    static class LocalVariableTableEntry {
        //代表该局部变量的生命周期开始的字节码偏移量
        private int startPC;
        //代表该局部变量的作用范围所覆盖的长度
        private int length;
        //指向常量池中个CONSTANT_Utf8_info型常量的索引，代表局部变量名称
        private int nameIdx;
        //指向常量池中个CONSTANT_Utf8_info型常量的索引，变量描述符
        private int descriptorIdx;
        //该局部变量在栈帧局部变量包中slot的位置
        private int idx;

        LocalVariableTableEntry(int startPC, int length, int nameIdx, int descriptorIdx, int idx) {
            this.startPC = startPC;
            this.length = length;
            this.nameIdx = nameIdx;
            this.descriptorIdx = descriptorIdx;
            this.idx = idx;
        }
    }

}
