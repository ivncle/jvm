package com.zqq.runtimedata.heap.methodarea;

import com.zqq.classfile.MemberInfo;
import com.zqq.runtimedata.heap.constantpoll.AccessFlags;

/**
 * 字段和方法都属于类的成员，它们有一些相同的信息（访问标志、名字、描述符）
 * 所以这里定义一个父类ClassMember用来存放字段和方法共同的部分；
 * 但是字段和方法不同的部分还需要分开处理:Filed;Method
 */
public class ClassMember {

    public int accessFlags;//访问标示
    public String name;//字段、方法名称
    public String descriptor;//字段、方法描述
    public Class clazz;//所属的类，这样可以通过字段或方法访问到它所属的类

    //从class文件的memberInfo中复制数据
    public void copyMemberInfo(MemberInfo memberInfo) {
        this.accessFlags = memberInfo.accessFlags();
        this.name = memberInfo.name();
        this.descriptor = memberInfo.descriptor();
    }

    public boolean isPublic() {
        return 0 != (this.accessFlags & AccessFlags.ACC_PUBLIC);
    }

    public boolean isPrivate() {
        return 0 != (this.accessFlags & AccessFlags.ACC_PRIVATE);
    }

    public boolean isProtected() {
        return 0 != (this.accessFlags & AccessFlags.ACC_PROTECTED);
    }

    public boolean isStatic() {
        return 0 != (this.accessFlags & AccessFlags.ACC_STATIC);
    }

    public boolean isFinal() {
        return 0 != (this.accessFlags & AccessFlags.ACC_FINAL);
    }

    public boolean isSynthetic() {
        return 0 != (this.accessFlags & AccessFlags.ACC_SYNTHETIC);
    }

    public String name() {
        return this.name;
    }

    public String descriptor() {
        return this.descriptor;
    }

    public Class clazz() {
        return this.clazz;
    }

    public boolean isAccessibleTo(Class d) {
        if (this.isPublic()) {
            return true;
        }
        Class c = this.clazz;
        if (this.isProtected()) {
            return d == c || c.getPackageName().equals(d.getPackageName());
        }
        if (!this.isPrivate()) {
            return c.getPackageName().equals(d.getPackageName());
        }
        return d == c;
    }

}
