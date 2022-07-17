package com.zqq.classfile.attributes;

import com.zqq.classfile.ClassReader;
import com.zqq.classfile.attributes.impl.*;
import com.zqq.classfile.constantpool.ConstantPool;

/**
 * 属性是可以扩展的，不同的虚拟机实现可以定义自己的属性类型
 * 属性表在JVM中的定义;
 * 虚拟机规范中,每个属性都定义了name_index,用来从常量池中拿到属性名;
 * attr_len,用来定义属性的的长度,便于接下来的解读
 * 其实很多属性的长度都是已知的,
 * 不确定长度的有:code属性,其长度需要根据len来读取;
 * attribute_info {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u1 info[attribute_length];
 * }
 */
public interface AttributeInfo {

    //抽象方法,由各属性自己读取对应的属性信息
    void readInfo(ClassReader reader);
    /**
     * 读取属性表;
     * 和ConstantPool中的方法类似,一般都是一下子全部读取出来,不会只读一个
     * 整个 JVM 中有三个地方用到了读取属性表
     * 1. 由 class 文件转为 ClassFile 对象时，读取 Class 的属性
     * 2. 为 class 中定义的 Field 和 Method 读取属性
     * 3. 为 Method 中的字节码读取属性(本地变量表大小，操作数大小，字节码，异常表)
     */
    static AttributeInfo[] readAttributes(ClassReader reader, ConstantPool constantPool) {
        int attributesCount = reader.readUint16();
        AttributeInfo[] attributes = new AttributeInfo[attributesCount];
        for (int i = 0; i < attributesCount; i++) {
            attributes[i] = readAttribute(reader, constantPool);
        }
        return attributes;
    }
    //读取单个属性
    static AttributeInfo readAttribute(ClassReader reader, ConstantPool constantPool) {
        int attrNameIdx = reader.readUint16();
        String attrName = constantPool.getUTF8(attrNameIdx);
        int attrLen = reader.readUint32TInteger();
        AttributeInfo attrInfo = newAttributeInfo(attrName, attrLen, constantPool);
        attrInfo.readInfo(reader);
        return attrInfo;
    }
    //Java虚拟机规范预定义了23种属性，先解析其中部分
    /**
     * 23种预定义属性可以分为三组。
     * 第一组属性是实现Java虚拟机所必需的，共有5种；
     * 第二组属性是Java类库所必需的，共有12种；
     * 第三组属性主要提供给工具使用，共有6种。第三组属性是可选的，也就是说可以不出现在class文件中。
     * (如果class文件中存在第三组属性，Java虚拟机实现或者Java类库也是可以利用它们的，比如使用LineNumberTable属性在异常堆栈中显示行号。)
     */
    static AttributeInfo newAttributeInfo(String attrName, int attrLen, ConstantPool constantPool) {
        switch (attrName) {
            case "BootstrapMethods":
                return new BootstrapMethodsAttribute();
            case "Code":
                return new CodeAttribute(constantPool);
            case "ConstantValue":
                return new ConstantValueAttribute();
            case "Deprecated":
                return new DeprecatedAttribute();
            case "EnclosingMethod":
                return new EnclosingMethodAttribute(constantPool);
            case "Exceptions":
                return new ExceptionsAttribute();
            case "InnerClasses":
                return new InnerClassesAttribute();
            case "LineNumberTable":
                return new LineNumberTableAttribute();
            case "LocalVariableTable":
                return new LocalVariableTableAttribute();
            case "LocalVariableTypeTable":
                return new LocalVariableTypeTableAttribute();
            // case "MethodParameters":
            // case "RuntimeInvisibleAnnotations":
            // case "RuntimeInvisibleParameterAnnotations":
            // case "RuntimeInvisibleTypeAnnotations":
            // case "RuntimeVisibleAnnotations":
            // case "RuntimeVisibleParameterAnnotations":
            // case "RuntimeVisibleTypeAnnotations":
            case "Signature":
                return new SignatureAttribute(constantPool);
            case "SourceFile":
                return new SourceFileAttribute(constantPool);
            // case "SourceDebugExtension":
            // case "StackMapTable":
            case "Synthetic":
                return new SyntheticAttribute();
            default:
                return new UnparsedAttribute(attrName, attrLen);
        }

    }

}
