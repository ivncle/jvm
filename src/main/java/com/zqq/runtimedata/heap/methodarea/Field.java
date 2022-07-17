package com.zqq.runtimedata.heap.methodarea;

import com.zqq.classfile.MemberInfo;
import com.zqq.classfile.attributes.impl.ConstantValueAttribute;
import com.zqq.runtimedata.heap.constantpoll.AccessFlags;

/**
 *  字段的抽象,是在class中定义的字段,包括静态的和非静态的
 */
public class Field extends ClassMember {
    //运行时常量池中的索引,该属性只有在static final成员有初值的情况下才有;
    public int constValueIndex;
    //类中字段数组slots中的的索引；其赋值在首次加载 class 文件后，为其分配的slotId
    //如果是静态字段，该 slotId 表示的是在 Class 中staticVars数组中的索引
    //如果是非静态字段，该 slotId 表示的是在 Object 中 fields 数组中的索引
    public int slotId;

    public Field[] newFields(Class clazz, MemberInfo[] cfFields) {
        Field[] fields = new Field[cfFields.length];
        for (int i = 0; i < cfFields.length; i++) {
            fields[i] = new Field();
            fields[i].clazz = clazz;
            fields[i].copyMemberInfo(cfFields[i]);
            fields[i].copyAttributes(cfFields[i]);
        }
        return fields;
    }

    public void copyAttributes(MemberInfo cfField) {
        ConstantValueAttribute valAttr = cfField.ConstantValueAttribute();
        if (null != valAttr) {
            this.constValueIndex = valAttr.constantValueIdx();
        }
    }

    public boolean isVolatile() {
        return 0 != (this.accessFlags & AccessFlags.ACC_VOLATILE);
    }

    public boolean isTransient() {
        return 0 != (this.accessFlags & AccessFlags.ACC_TRANSIENT);
    }

    public boolean isEnum() {
        return 0 != (this.accessFlags & AccessFlags.ACC_ENUM);
    }

    public int constValueIndex() {
        return this.constValueIndex;
    }

    public int slotId() {
        return this.slotId;
    }

    public boolean isLongOrDouble() {
        return this.descriptor.equals("J") || this.descriptor.equals("D");
    }

}
