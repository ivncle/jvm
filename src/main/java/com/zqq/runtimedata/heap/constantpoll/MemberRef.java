package com.zqq.runtimedata.heap.constantpoll;

import com.zqq.classfile.constantpool.impl.ConstantMemberRefInfo;

import java.util.Map;

/**
 * 字段和方法的符号引用保存的相同信息;包含全限名和描述符;
 * 字段和方法特有的属性,有其对应子类来实现;
 */
public class MemberRef extends SymRef {
    //字段或方法名
    public String name;
    //字段或方法描述符
    public String descriptor;

    public void copyMemberRefInfo(ConstantMemberRefInfo refInfo){
        this.className = refInfo.className();
        Map<String, String> map = refInfo.nameAndDescriptor();
        this.name = map.get("name");
        this.descriptor = map.get("_type");
    }

    public String name(){
        return this.name;
    }

    public String descriptor(){
        return this.descriptor;
    }

}
