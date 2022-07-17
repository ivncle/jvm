package com.zqq.classfile;

import com.zqq.classfile.attributes.AttributeInfo;
import com.zqq.classfile.attributes.impl.CodeAttribute;
import com.zqq.classfile.attributes.impl.ConstantValueAttribute;
import com.zqq.classfile.constantpool.ConstantPool;

/**
 * 字段表和方法表，共用该类，因为二者在虚拟机规范中的定义是相同的
 * 里面包含的是类中的所定义的成员变量/方法
 * 字段/方法中可能还包含属性
 * 静态的和非静态的都包含
 */
public class MemberInfo {
    //常量池
    private ConstantPool constantPool;
    //访问权限
    private int accessFlags;
    //名字索引
    private int nameIdx;
    //描述索引
    private int descriptorIdx;
    //属性信息
    private AttributeInfo[] attributes;

    private MemberInfo(ClassReader reader, ConstantPool constantPool) {
        this.constantPool = constantPool;
        this.accessFlags = reader.readUint16();
        this.nameIdx = reader.readUint16();
        this.descriptorIdx = reader.readUint16();
        this.attributes = AttributeInfo.readAttributes(reader, constantPool);
    }
    //读取成员信息
    static MemberInfo[] readMembers(ClassReader reader, ConstantPool constantPool) {
        int fieldCount = reader.readUint16();
        MemberInfo[] fields = new MemberInfo[fieldCount];
        for (int i = 0; i < fieldCount; i++) {
            fields[i] = new MemberInfo(reader, constantPool);
        }
        return fields;
    }

    public int accessFlags() {
        return this.accessFlags;
    }

    public String name() {
        return this.constantPool.getUTF8(this.nameIdx);
    }

    public String descriptor() {
        return this.constantPool.getUTF8(this.descriptorIdx);
    }
    //属性表获取代码
    public CodeAttribute codeAttribute() {
        for (AttributeInfo attrInfo : attributes) {
            if (attrInfo instanceof CodeAttribute) return (CodeAttribute) attrInfo;
        }
        return null;
    }
    //获取常量属性
    public ConstantValueAttribute ConstantValueAttribute() {
        for (AttributeInfo attrInfo : attributes) {
            if (attrInfo instanceof ConstantValueAttribute) return (ConstantValueAttribute) attrInfo;
        }
        return null;
    }

}
