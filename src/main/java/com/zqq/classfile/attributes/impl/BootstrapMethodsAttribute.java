package com.zqq.classfile.attributes.impl;

import com.zqq.classfile.ClassReader;
import com.zqq.classfile.attributes.AttributeInfo;

/**
 * BootstrapMethods属性记录InvokedDynamic指令引用的引导方法列表
 * BootstrapMethods_attribute {
 *  u2 attribute_name_index;
 *  u4 attribute_length;
 *  u2 num_bootstrap_methods;
 *  {   u2 bootstrap_method_ref;
 *      u2 num_bootstrap_arguments;
 *      u2 bootstrap_arguments[num_bootstrap_arguments];
 *  } bootstrap_methods[num_bootstrap_methods];
 * }
 */
public class BootstrapMethodsAttribute implements AttributeInfo {

    BootstrapMethod[] bootstrapMethods;

    @Override
    public void readInfo(ClassReader reader) {
        int bootstrapMethodNum = reader.readUint16();
        bootstrapMethods = new BootstrapMethod[bootstrapMethodNum];
        for (int i = 0; i < bootstrapMethodNum; i++) {
            bootstrapMethods[i] = new BootstrapMethod(reader.readUint16(), reader.readUint16s());
        }
    }

    static class BootstrapMethod {
        //ConstantMethodHandleInfo索引
        int bootstrapMethodRef;
        //参数个数
        int[] bootstrapArguments;

        BootstrapMethod(int bootstrapMethodRef, int[] bootstrapArguments) {
            this.bootstrapMethodRef = bootstrapMethodRef;
            this.bootstrapArguments = bootstrapArguments;
        }
    }

}
