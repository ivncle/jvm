package com.zqq.classfile.attributes.impl;

import com.zqq.classfile.ClassReader;

/**
 * 仅起标记作用，不包含任何数据。是JDK1.1引入的，可以出现在 ClassFile、field_info和method_info结构中(表示弃用)
 * 属于布尔属性，只有存在和不存在的区别。
 */
public class DeprecatedAttribute extends MarkerAttribute {


    @Override
    public void readInfo(ClassReader reader) {

    }

}
