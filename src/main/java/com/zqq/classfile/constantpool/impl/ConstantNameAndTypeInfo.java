package com.zqq.classfile.constantpool.impl;

import com.zqq.classfile.ClassReader;
import com.zqq.classfile.constantpool.ConstantInfo;

/**
 * name_index和descriptor_index都是常量池索引，指向CONSTANT_Utf8_info常量。
 * 字段或方法名由name_index给出，对应的就是代码中真实的成员变量名或者方法名
 * 字段或方法的描述符由descriptor_index给出
 * 描述符:描述字段的类型,描述方法的参数类型;
 * a:基本类型byte、short、char、int、long、float和double的描述符是单个字母，分别对应B、S、C、I、J、F和D。注意，long的描述符是J而不是L。
 * b:引用类型的描述符是 L ＋ 类的完全限定名 ＋ 分号 eg: Ljava.lang.String;
 * c:数组类型的描述符是[＋数组元素类型描述符。eg: [I  代表int[]
 * 字段描述符就是字段类型的描述符。
 * 方法描述符是（分号分隔的参数类型描述符）+返回值类型描述符，其中void返回值由单个字母V表示。eg:(Ljava.lang.String;I)
 */
public class ConstantNameAndTypeInfo implements ConstantInfo {
    //字段或者方法的有效索引
    public int nameIdx;
    public int descIdx;

    @Override
    public void readInfo(ClassReader reader) {
        this.nameIdx = reader.readUint16();
        this.descIdx = reader.readUint16();
    }

    @Override
    public int tag() {
        return this.CONSTANT_TAG_NAMEANDTYPE;
    }

}
