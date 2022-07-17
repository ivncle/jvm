package com.zqq.instructions.references;

import com.zqq.instructions.base.InstructionIndex16;
import com.zqq.instructions.base.MethodInvokeLogic;
import com.zqq.runtimedata.Frame;
import com.zqq.runtimedata.OperandStack;
import com.zqq.runtimedata.heap.constantpoll.MethodRef;
import com.zqq.runtimedata.heap.constantpoll.RunTimeConstantPool;
import com.zqq.runtimedata.heap.methodarea.*;
import com.zqq.runtimedata.heap.methodarea.Class;
import com.zqq.runtimedata.heap.methodarea.Object;

/**
 * 调用虚方法(除了静态方法、私有方法、final方法、实例构造器、父类方法外都是虚方法)
 */
public class INVOKE_VIRTUAL extends InstructionIndex16 {

    @Override
    public void execute(Frame frame) {
        //调用该方法所在的类
        Class currentClass = frame.method().clazz();
        RunTimeConstantPool runTimeConstantPool = currentClass.constantPool();
        //通过index,拿到方法符号引用,虚方法(用到了多态),这个方法引用指向的其实是父类的
        MethodRef methodRef = (MethodRef) runTimeConstantPool.getConstants(this.idx);
        //将方法引用转换为方法
        //这一步拿到解析后的resolvedMethod主要是用来做下面权限的验证;
        //而真正的resolvedMethod是在下面拿到真正的调用者,再次解析到的methodToBeInvoked
        Method resolvedMethod = methodRef.ResolvedMethod();

        if (resolvedMethod.isStatic()) {
            throw new IncompatibleClassChangeError();
        }
        //从操作数栈中获取调用该非静态方法的引用
        Object ref = frame.operandStack().getRefFromTop(resolvedMethod.argSlotCount() - 1);
        if (null == ref) {
            if ("println".equals(methodRef.name())) {
                _println(frame.operandStack(), methodRef.descriptor());
                return;
            }
            throw new NullPointerException();
        }
        //验证protected的方法的调用权限
        if (resolvedMethod.isProtected() &&
                resolvedMethod.clazz().isSubClassOf(currentClass) &&
                !resolvedMethod.clazz().getPackageName().equals(currentClass.getPackageName()) &&
                ref.clazz() != currentClass &&
                !ref.clazz().isSubClassOf(currentClass)) {
            throw new IllegalAccessError();
        }
        //相对于invokespecial,本指令还多了这一步,因为ref才是真正的调用者
        //而这次解析到的才是真正的method,这是多态的核心!
        Method methodToBeInvoked = MethodLookup.lookupMethodInClass(ref.clazz(), methodRef.name(), methodRef.descriptor());
        if (null == methodToBeInvoked || methodToBeInvoked.isAbstract()) {
            throw new AbstractMethodError();
        }

        MethodInvokeLogic.invokeMethod(frame, methodToBeInvoked);
    }

    //hack
    private void _println(OperandStack stack, String descriptor) {
        switch (descriptor) {
            case "(Z)V":
                System.out.println(stack.popInt() != 0);
                break;
            case "(C)V":
                System.out.println(stack.popInt());
                break;
            case "(I)V":
            case "(B)V":
            case "(S)V":
                System.out.println(stack.popInt());
                break;
            case "(F)V":
                System.out.println(stack.popFloat());
                break;
            case "(J)V":
                System.out.println(stack.popLong());
                break;
            case "(D)V":
                System.out.println(stack.popDouble());
                break;
            case "(Ljava/lang/String;)V":
                Object jStr = stack.popRef();
                String goStr = StringPool.goString(jStr);
                System.out.println(goStr);
                break;
            default:
                System.out.println(descriptor);
                break;
        }
        stack.popRef();
    }
}


