package com.zqq.classfile.constantpool;

import com.zqq.classfile.ClassReader;
import com.zqq.classfile.constantpool.impl.ConstantClassInfo;
import com.zqq.classfile.constantpool.impl.ConstantNameAndTypeInfo;
import com.zqq.classfile.constantpool.impl.ConstantUtf8Info;

import java.util.HashMap;
import java.util.Map;

/**
 * 常量池实际上也是一个表,这里用数组来实现，所以常量池这个类中持有一个常量数据表
 * 至于这个数组的每一项的初始化，则根据读到的字节的tag不同而创建不同的常量子类
 * 常量池有多种类型，这个根据读取到的tag的不同创建不同的常量类，
 */
public class ConstantPool {
    //保存类文件常量池中的所有常量
    private ConstantInfo[] constantInfos;
    private final int size;

    public ConstantPool(ClassReader reader) {
        size = reader.readUint16();
        constantInfos = new ConstantInfo[size];
        //从1开始计算
        for (int i = 1; i < size; i++) {

            constantInfos[i] = ConstantInfo.readConstantInfo(reader, this);
            //double和long占两个槽位
            switch (constantInfos[i].tag()) {
                case ConstantInfo.CONSTANT_TAG_DOUBLE:
                case ConstantInfo.CONSTANT_TAG_LONG:
                    i++;
                    break;
            }
        }
    }
    //根据常量池索引获取常量名字和类型
    public Map<String, String> getNameAndType(int idx) {
        ConstantNameAndTypeInfo constantInfo = (ConstantNameAndTypeInfo) this.constantInfos[idx];
        Map<String, String> map = new HashMap<>();
        map.put("name", this.getUTF8(constantInfo.nameIdx));
        map.put("_type", this.getUTF8(constantInfo.descIdx));
        return map;
    }
    //根据ConstantClassInfo中的索引获得ConstantUtf8Info中的值
    public String getClassName(int idx){
        ConstantClassInfo classInfo = (ConstantClassInfo) this.constantInfos[idx];
        return this.getUTF8(classInfo.nameIdx);
    }
    //根据索引读取ConstantUtf8Info中的值
    public String getUTF8(int idx) {
        ConstantUtf8Info utf8Info = (ConstantUtf8Info) this.constantInfos[idx];
        return utf8Info == null ? "" : utf8Info.str();
    }
    //获取常量表
    public ConstantInfo[] getConstantInfos() {
        return constantInfos;
    }

    public void setConstantInfos(ConstantInfo[] constantInfos) {
        this.constantInfos = constantInfos;
    }

    public int getSize() {
        return size;
    }
}
