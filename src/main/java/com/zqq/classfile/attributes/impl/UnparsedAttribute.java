package com.zqq.classfile.attributes.impl;

import com.zqq.classfile.ClassReader;
import com.zqq.classfile.attributes.AttributeInfo;

/**
 * 并未实现所有属性，这该类来代替未实现的属性
 */
public class UnparsedAttribute implements AttributeInfo {

    private String name;
    private int length;
    private byte[] info;

    public UnparsedAttribute(String attrName, int attrLen) {
        this.name = attrName;
        this.length = attrLen;
    }

    @Override
    public void readInfo(ClassReader reader) {
        this.info = reader.readBytes(this.length);
    }

    public byte[] info(){
        return this.info;
    }

}
