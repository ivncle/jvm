package com.zqq.runtimedata.heap.methodarea;

import com.zqq.classfile.MemberInfo;
import com.zqq.classfile.attributes.impl.CodeAttribute;
import com.zqq.runtimedata.heap.constantpoll.AccessFlags;

import java.util.List;

public class Method extends ClassMember {

    public int maxStack;
    public int maxLocals;
    public byte[] code;
    //方法所需的参数个数;对于非静态方法,至少是1个(this)
    private int argSlotCount;

    Method[] newMethods(Class clazz, MemberInfo[] cfMethods) {
        Method[] methods = new Method[cfMethods.length];
        for (int i = 0; i < cfMethods.length; i++) {
            methods[i] = newMethod(clazz, cfMethods[i]);
        }
        return methods;
    }

    private Method newMethod(Class clazz, MemberInfo cfMethod) {
        Method method = new Method();
        method.clazz = clazz;
        //复制成员信息
        method.copyMemberInfo(cfMethod);
        //复制方法属性信息
        method.copyAttributes(cfMethod);
        //解析方法描述符
        MethodDescriptor md = MethodDescriptorParser.parseMethodDescriptorParser(method.descriptor);
        //计算参数槽位数
        method.calcArgSlotCount(md.parameterTypes);

        if (method.isNative()) {
            method.injectCodeAttribute(md.returnType);
        }
        return method;
    }
    //JVM 并没有规定如何实现和调用本地方法，这里我们依然使用 JVM 栈 来执行本地方法
    //但是本地方法中并不包含字节码，那么本地方法的调用，这里我们利用接口来实现调用对应的方法；
    //同时 JVM 中预留了两条指令，操作码分别是 0xff 和 0xfe，下面使用 0xfe 来当前方法为表示本地方法
    //第二个字节为本地方法的返回指令，该指令和普通方法的返回指令是一样的。
    private void injectCodeAttribute(String returnType) {
        //本地方法的操作数栈暂时为4;至少能容纳返回值
        this.maxStack = 4; //todo
        //本地方法的局部变量表只用来存放参数,因此直接这样赋值
        this.maxLocals = this.argSlotCount;
        //接下来为本地方法构造字节码:起始第一个字节都是0xfe,用来表用这是本地方法;
        //第二个字节码则根据不同的返回值类型选择相应的xreturn的指令即可
        switch (returnType.getBytes()[0]) {
            case 'V':
                this.code = new byte[]{(byte) 0xfe, (byte) 0xb1};
                break;
            case 'L':
            case '[':
                this.code = new byte[]{(byte) 0xfe, (byte) 0xb0};
                break;
            case 'D':
                this.code = new byte[]{(byte) 0xfe, (byte) 0xaf};
                break;
            case 'F':
                this.code = new byte[]{(byte) 0xfe, (byte) 0xae};
                break;
            case 'J':
                this.code = new byte[]{(byte) 0xfe, (byte) 0xad};
                break;
            default:
                this.code = new byte[]{(byte) 0xfe, (byte) 0xac};
                break;
        }
    }

    private void copyAttributes(MemberInfo cfMethod) {
        CodeAttribute codeAttr = cfMethod.codeAttribute();
        if (null != codeAttr) {
            this.maxStack = codeAttr.maxStack();
            this.maxLocals = codeAttr.maxLocals();
            this.code = codeAttr.data();
        }
    }

    private void calcArgSlotCount(List<String> parameterTypes) {
        for (String paramType : parameterTypes) {
            this.argSlotCount++;
            if ("J".equals(paramType) || "D".equals(paramType)) {
                this.argSlotCount++;
            }
        }
        if (!this.isStatic()) {
            this.argSlotCount++;
        }
    }

    public boolean isSynchronized() {
        return 0 != (this.accessFlags & AccessFlags.ACC_SYNCHRONIZED);
    }

    public boolean isBridge() {
        return 0 != (this.accessFlags & AccessFlags.ACC_BRIDGE);
    }

    public boolean isVarargs() {
        return 0 != (this.accessFlags & AccessFlags.ACC_VARARGS);
    }

    public boolean isNative() {
        return 0 != (this.accessFlags & AccessFlags.ACC_NATIVE);
    }

    public boolean isAbstract() {
        return 0 != (this.accessFlags & AccessFlags.ACC_ABSTRACT);
    }

    public boolean isStrict() {
        return 0 != (this.accessFlags & AccessFlags.ACC_STRICT);
    }

    public int maxStack() {
        return this.maxStack;
    }

    public int maxLocals() {
        return this.maxLocals;
    }

    public byte[] code() {
        return this.code;
    }

    public int argSlotCount() {
        return this.argSlotCount;
    }

}
