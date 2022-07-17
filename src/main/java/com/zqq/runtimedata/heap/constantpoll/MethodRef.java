package com.zqq.runtimedata.heap.constantpoll;

import com.zqq.classfile.constantpool.impl.ConstantMethodRefInfo;
import com.zqq.runtimedata.heap.methodarea.Class;
import com.zqq.runtimedata.heap.methodarea.Method;
import com.zqq.runtimedata.heap.methodarea.MethodLookup;

/**
 * 非接口方法引用
 */
public class MethodRef extends MemberRef {

    private Method method;

    public static MethodRef newMethodRef(RunTimeConstantPool runTimeConstantPool, ConstantMethodRefInfo refInfo) {
        MethodRef ref = new MethodRef();
        ref.runTimeConstantPool = runTimeConstantPool;
        ref.copyMemberRefInfo(refInfo);
        return ref;
    }
    //非接口方法引用解析成直接引用
    public Method ResolvedMethod() {
        if (null == this.method) {
            this.resolveMethodRef();
        }
        return this.method;
    }

    private void resolveMethodRef() {
        Class d = this.runTimeConstantPool.getClazz();
        //获取 methodRef 所在的类
        Class c = this.resolvedClass();
        if (c.isInterface()) {
            throw new IncompatibleClassChangeError();
        }
        //在该类中找到对应的方法
        Method method = lookupMethod(c, this.name, this.descriptor);
        if (null == method){
            throw new NoSuchMethodError();
        }

        if (!method.isAccessibleTo(d)){
            throw new IllegalAccessError();
        }

        this.method = method;
    }
    //需验证方法引用，在父类找不到后，是否需要从其接口中再去找
    public Method lookupMethod(Class clazz, String name, String descriptor) {
        Method method = MethodLookup.lookupMethodInClass(clazz, name, descriptor);
        if (null == method) {
            method = MethodLookup.lookupMethodInInterfaces(clazz.interfaces, name, descriptor);
        }
        return method;
    }

}
