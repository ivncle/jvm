package com.zqq.runtimedata.heap.constantpoll;

import com.zqq.runtimedata.heap.methodarea.Class;

/**
 * 保存符号引用的一些共有属性;
 */
public class SymRef {
    //存放符号引用所在的运行时常量池指针,可以通过符号引用访问到运行时常量池，进一步又可以访问到类数据
    public RunTimeConstantPool runTimeConstantPool;
    //存放类的完全限定名
    public String className;
    //运行时常量池的宿主类中的符号引用的真正类,在外面访问时，根据 clazz 是否为 null 来决定是否执行 loadClass
    public Class clazz;
    //解析类
    public Class resolvedClass() {
        if (null != this.clazz) return this.clazz;
        Class d = this.runTimeConstantPool.getClazz();
        Class c = d.loader.loadClass(this.className);;
        this.clazz = c;
        return this.clazz;
    }

}
