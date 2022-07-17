package com.zqq.classfile.attributes.impl;

import com.zqq.classfile.ClassReader;
import com.zqq.classfile.attributes.AttributeInfo;

/**
 * ConstantValue是定长属性，只会出现在field_info结构中。
 * 其作用是通知JVM自动为静态变量赋值。只有被static final关键字修饰的变量才有这个属性。
 * 对于以下三种情况：
 * int a1 = 123;
 * static int a2 = 123;
 * final static int a3 = 123;
 * a1是实例变量，其赋值是在实例构造器<init>方法中完成的。
 * 而对于a2和a3，他们都是类变量，那么其赋值有两种情况，一种是在<clinit>,一种是使用ConstantValue属性；
 * 目前Sun Javac 的选择是：a3 使用生成 ConstantValue 属性的方法来赋值
 * a2则将会在<clinit>中进行赋值。
 */
public class ConstantValueAttribute implements AttributeInfo {

    private int constantValueIdx;

    @Override
    public void readInfo(ClassReader reader) {
        this.constantValueIdx = reader.readUint16();
    }

    public int constantValueIdx(){
        return this.constantValueIdx;
    }

}
