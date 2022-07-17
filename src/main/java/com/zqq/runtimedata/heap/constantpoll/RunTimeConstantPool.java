package com.zqq.runtimedata.heap.constantpoll;

import com.zqq.classfile.constantpool.ConstantInfo;
import com.zqq.classfile.constantpool.ConstantPool;
import com.zqq.classfile.constantpool.impl.*;
import com.zqq.runtimedata.heap.methodarea.Class;

/**
 * 运行时常量池
 * 运行时常量池,注意和字节码中的常量池做区分，这里指的是线程共享区的常量；
 * 实现的功能是：把class文件中的常量池转换成运行时常量池
 * 这里对两种常量池做了区分，class 文件中的常量池仍然用：ConstantPool
 * 而运行时常量池使用的是：RuntimeConstantPool
 */
public class RunTimeConstantPool {

    private Class clazz;
    private java.lang.Object[] constants;

    public RunTimeConstantPool(Class clazz, ConstantPool constantPool) {
        int cpCount = constantPool.getConstantInfos().length;
        this.clazz = clazz;
        this.constants = new java.lang.Object[cpCount];

        ConstantInfo[] constantInfos = constantPool.getConstantInfos();
        for (int i = 1; i < cpCount; i++) {
            ConstantInfo constantInfo = constantInfos[i];

            switch (constantInfo.tag()) {
                case ConstantInfo.CONSTANT_TAG_INTEGER:
                    ConstantIntegerInfo integerInfo = (ConstantIntegerInfo) constantInfo;
                    this.constants[i] = integerInfo.value();
                    break;
                case ConstantInfo.CONSTANT_TAG_FLOAT:
                    ConstantFloatInfo floatInfo = (ConstantFloatInfo) constantInfo;
                    this.constants[i] = floatInfo.value();
                    break;
                case ConstantInfo.CONSTANT_TAG_LONG:
                    ConstantLongInfo longInfo = (ConstantLongInfo) constantInfo;
                    this.constants[i] = longInfo.value();
                    i++;
                    break;
                case ConstantInfo.CONSTANT_TAG_DOUBLE:
                    ConstantDoubleInfo doubleInfo = (ConstantDoubleInfo) constantInfo;
                    this.constants[i] = doubleInfo.value();
                    i++;
                    break;
                case ConstantInfo.CONSTANT_TAG_STRING:
                    ConstantStringInfo stringInfo = (ConstantStringInfo) constantInfo;
                    this.constants[i] = stringInfo.string();
                    break;
                case ConstantInfo.CONSTANT_TAG_CLASS:
                    ConstantClassInfo classInfo = (ConstantClassInfo) constantInfo;
                    this.constants[i] = ClassRef.newClassRef(this, classInfo);
                    break;
                case ConstantInfo.CONSTANT_TAG_FIELDREF:
                    this.constants[i] = FieldRef.newFieldRef(this, (ConstantFieldRefInfo) constantInfo);
                    break;
                case ConstantInfo.CONSTANT_TAG_INTERFACEMETHODREF:
                    this.constants[i] = InterfaceMethodRef.newInterfaceMethodRef(this, (ConstantInterfaceMethodRefInfo) constantInfo);
                    break;
                case ConstantInfo.CONSTANT_TAG_METHODREF:
                    this.constants[i] = MethodRef.newMethodRef(this, (ConstantMethodRefInfo) constantInfo);
                    break;
                default:
            }
        }
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public java.lang.Object getConstants(int idx) {
        return constants[idx];
    }

}
