package com.zqq.instructions.base;

import com.zqq.runtimedata.Frame;
import com.zqq.runtimedata.Thread;
import com.zqq.runtimedata.heap.methodarea.Class;
import com.zqq.runtimedata.heap.methodarea.Method;

/**
 * jvm class初始化类
 */
public class ClassInitLogic {

    public static void initClass(Thread thread, Class clazz) {
        clazz.startInit();
        scheduleClinit(thread, clazz);
        initSuperClass(thread, clazz);
    }
    //clinit方法入栈
    private static void scheduleClinit(Thread thread, Class clazz) {
        //<clinit>
        Method clinit = clazz.getClinitMethod();
        if (null == clinit) return;
        Frame newFrame = thread.newFrame(clinit);
        thread.pushFrame(newFrame);
    }
    //初始化父类
    private static void initSuperClass(Thread thread, Class clazz) {
        if (clazz.isInterface()) return;
        Class superClass = clazz.superClass();
        if (null != superClass && !superClass.initStarted()) {
            initClass(thread, superClass);
        }
    }

}
