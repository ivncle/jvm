package com.zqq.runtimedata.heap.methodarea;

import java.util.ArrayList;
import java.util.List;

/**
 * jvm中的方法描述符
 */
public class MethodDescriptor {

    public List<String> parameterTypes = new ArrayList<>();
    public String returnType;

    public void addParameterType(String type){
        this.parameterTypes.add(type);
    }

}
