package com.zqq.instructions.references;

import com.zqq.instructions.base.InstructionIndex16;
import com.zqq.instructions.base.MethodInvokeLogic;
import com.zqq.runtimedata.Frame;
import com.zqq.runtimedata.heap.constantpoll.MethodRef;
import com.zqq.runtimedata.heap.constantpoll.RunTimeConstantPool;
import com.zqq.runtimedata.heap.methodarea.Class;
import com.zqq.runtimedata.heap.methodarea.Method;
import com.zqq.runtimedata.heap.methodarea.MethodLookup;
import com.zqq.runtimedata.heap.methodarea.Object;

/**
 * 调用无须动态绑定的实例方法(包括：构造函数,私有方法,通过super关键字调用的超类方法)
 * JVM特意为这种不需要动态绑定的方法创建的invokespecial,目的是为了加快方法调用(解析)的速度
 */
public class INVOKE_SPECIAL extends InstructionIndex16 {

    @Override
    public void execute(Frame frame) {
        //调用该方法所在的类
        Class currentClass = frame.method().clazz();
        RunTimeConstantPool runTimeConstantPool = currentClass.constantPool();
        //通过index,拿到方法符号引用
        MethodRef methodRef = (MethodRef) runTimeConstantPool.getConstants(this.idx);
        //和静态方法不同的是,要先加载方法所在的类
        Class resolvedClass = methodRef.resolvedClass();
        //将方法引用转换为方法
        Method resolvedMethod = methodRef.ResolvedMethod();
        //<init>方法必须在其对应的类进行声明,这里必须要验证类是否匹配
        if ("<init>".equals(resolvedMethod.name()) && resolvedMethod.clazz() != resolvedClass) {
            throw new NoSuchMethodError();
        }
        if (resolvedMethod.isStatic()) {
            throw new IncompatibleClassChangeError();
        }
        //从操作数栈中获取调用该非静态方法的引用
        Object ref = frame.operandStack().getRefFromTop(resolvedMethod.argSlotCount() - 1);
        if (null == ref) {
            throw new NullPointerException();
        }

        if (resolvedMethod.isProtected() &&
                resolvedMethod.clazz().isSubClassOf(currentClass) &&
                !resolvedMethod.clazz().getPackageName().equals(currentClass.getPackageName()) &&
                ref.clazz() != currentClass &&
                !ref.clazz().isSubClassOf(currentClass)) {
            throw new IllegalAccessError();
        }

        Method methodToBeInvoked = resolvedMethod;

        //首先 ACC_SUPER:在jdk1.02之后编译出来的类,该标志均为真;
        //解决 super.func()的形式;但是不能是<init>方法;
        //父类中可能定义了func方法,同时子类又覆盖了父类的func,
        //那么解析func的符号引用时首先能在子类中解析到,但此时显示的调用了父类的func方法,所以还需要在父类中去解析;
        if (currentClass.isSuper() &&
                resolvedClass.isSubClassOf(currentClass) &&
                !resolvedMethod.name().equals("<init>")) {
            methodToBeInvoked = MethodLookup.lookupMethodInClass(currentClass.superClass, methodRef.name(), methodRef.descriptor());
        }

        if (methodToBeInvoked.isAbstract()) {
            throw new AbstractMethodError();
        }

        MethodInvokeLogic.invokeMethod(frame, methodToBeInvoked);

    }

}