package com.zqq.runtimedata;

import com.zqq.runtimedata.heap.methodarea.Object;

/**
 * 数据槽
 */
public class Slot {
    //存基本类型
    public int num;
    //存jvm中的对象
    public Object ref;

}
