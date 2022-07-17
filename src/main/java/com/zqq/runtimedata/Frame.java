package com.zqq.runtimedata;

import com.zqq.runtimedata.heap.methodarea.Method;

/**
 * 栈帧
 */
public class Frame {

    //stack is implemented as linked list
    Frame lower;

    //局部变量表
    private LocalVars localVars;

    //操作数栈
    private OperandStack operandStack;

    //当前栈帧所在线程
    private Thread thread;

    //动态链接(栈帧里面存的运行时常量池指向这个栈帧所代表方法的引用，可根据这个直接引用找到方法入口)
    private Method method;

    //frame中并不改变PC的值,其PC值是由ByteReader读取字节码不断改变的
    private int nextPC;


    public Frame(Thread thread, Method method) {
        this.thread = thread;
        this.method = method;
        this.localVars = new LocalVars(method.maxLocals);
        this.operandStack = new OperandStack(method.maxStack);
    }

    public LocalVars localVars() {
        return localVars;
    }

    public OperandStack operandStack() {
        return operandStack;
    }

    public Thread thread() {
        return this.thread;
    }

    public Method method(){
        return this.method;
    }

    public int nextPC() {
        return this.nextPC;
    }

    public void setNextPC(int nextPC) {
        this.nextPC = nextPC;
    }
    //用在new，getStatic，invokeStatic 等指令中，判断clinit 方法是否执行，如果未执行，则需要保存当前thread 的 pc
    //eg：当前执行的是 new 指令，那么 thead 的 pc 指向的是 new，
    //再 push 一个新栈去执行<clinit>，等<clinit>直接结束后，在回到当前 frame，拿到 pc，此时的 pc 指向的还是 new
    //重新执行一遍 new
    public void revertNextPC(){
        this.nextPC = this.thread.pc();
    }

}
