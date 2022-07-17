package com.zqq.classfile.constantpool.impl;

import com.zqq.classfile.ClassReader;
import com.zqq.classfile.constantpool.ConstantInfo;

/**
 * 可以分为两类
 * 真正存放字符串ConstantUtf8Info
 * 这里就涉及到 class 文件中ConstantUtf8Info常量保存的字符串是什么内容了：
 * 类名，方法名，变量名，描述符，这些字符串是用来确定加载哪个类，调用哪个方法，使用哪个变量的，是供 JVM 内部使用的。
 *  ConstantStringInfo
 * 在 java 程序中显示声明的 String 类型的变量，是供 java 程序使用的。
 * 我们写 java 程序其实只关心我们定义的 String 类型的变量，而 String 类型的变量在 class 文件中就是用 ConstantStringInfo常量来表示的。
 * 这就解释了为什么都是表示字符串，使用两种常量进行区分。这里主要是把 JVM 使用的字符串和 java 应用程序使用的字符串所区分开。
 * CONSTANT_Utf8_info {
 *  u1 tag;
 *  u2 length;
 *  u1 bytes[length];
 * }
 */
public class ConstantUtf8Info implements ConstantInfo {

    private String str;

    @Override
    public void readInfo(ClassReader reader) {
        int length = reader.readUint16();
        byte[] bytes = reader.readBytes(length);
        this.str = new String(bytes);
    }

    @Override
    public int tag() {
        return this.CONSTANT_TAG_UTF8;
    }

    public String str(){
        return this.str;
    }

}
