package com.zqq;

import com.zqq._native.Registry;
import com.zqq.instructions.Factory;
import com.zqq.instructions.base.BytecodeReader;
import com.zqq.instructions.base.Instruction;
import com.zqq.runtimedata.Frame;
import com.zqq.runtimedata.Thread;
import com.zqq.runtimedata.heap.ClassLoader;
import com.zqq.runtimedata.heap.methodarea.Class;
import com.zqq.runtimedata.heap.methodarea.Method;
import com.zqq.runtimedata.heap.methodarea.Object;
import com.zqq.runtimedata.heap.methodarea.StringPool;

//指令集解释器
class Interpret {

//    Interpret(Method method, boolean logInst) {
//        Thread thread = new Thread();
//        Frame frame = thread.newFrame(method);
//        thread.pushFrame(frame);
//
//        loop(thread, logInst);
//    }

//    Interpret(Method method) {
//        Thread thread = new Thread();
//        //帧
//        Frame frame = thread.newFrame(method);
//        thread.pushFrame(frame);
//        loop(thread, method.code());
//    }

    //    private void loop(Thread thread, byte[] byteCode) {
//        Frame frame = thread.popFrame();
//        //读取字节码
//        BytecodeReader reader = new BytecodeReader();
//
//        while (true) {
//            //循环
//            int pc = frame.nextPC();
//            thread.setPC(pc);
//            //decode
//            reader.reset(byteCode, pc);
//            byte opcode = reader.readByte();
//            Instruction inst = Factory.newInstruction(opcode);
//            if (null == inst) {
//                System.out.format("Unsupported opcode：0x%x\n", opcode);
//                break;
//            }
//            inst.fetchOperands(reader);
//            frame.setNextPC(reader.pc());
//            System.out.format("寄存器(指令)：0x%x -> %s => 局部变量表：%s 操作数栈：%s\n", opcode, inst.getClass().getSimpleName(), JSON.toJSONString(frame.localVars().getSlots()), JSON.toJSONString(frame.operandStack().getSlots()));
//            //exec
//            inst.execute(frame);
//        }
//
//    }
//    private void loop(Thread thread, boolean logInst) {
//        BytecodeReader reader = new BytecodeReader();
//        while (true) {
//            Frame frame = thread.currentFrame();
//            int pc = frame.nextPC();
//            thread.setPC(pc);
//
//            reader.reset(frame.method().code, pc);
//            byte opcode = reader.readByte();
//            Instruction inst = Factory.newInstruction(opcode);
//            if (null == inst) {
//                System.out.println("Unsupported opcode " + byteToHexString(new byte[]{opcode}));
//                break;
//            }
//            inst.fetchOperands(reader);
//            frame.setNextPC(reader.pc());
//
//            if (logInst) {
//                logInstruction(frame, inst, opcode);
//            }
//
//            //exec
//            inst.execute(frame);
//
//            if (thread.isStackEmpty()) {
//                break;
//            }
//        }
//    }
//
//    private static void logInstruction(Frame frame, Instruction inst, byte opcode) {
//        Method method = frame.method();
//        String className = method.clazz().name();
//        String methodName = method.name();
//        String outStr = (className + "." + methodName + "() \t") +
//                "寄存器(指令)：" + byteToHexString(new byte[]{opcode}) + " -> " + inst.getClass().getSimpleName() + " => 局部变量表：" + JSON.toJSONString(frame.localVars().getSlots()) + " 操作数栈：" + JSON.toJSONString(frame.operandStack().getSlots());
//        System.out.println(outStr);
//    }
//
//    private static String byteToHexString(byte[] codes) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("0x");
//        for (byte b : codes) {
//            int value = b & 0xFF;
//            String strHex = Integer.toHexString(value);
//            if (strHex.length() < 2) {
//                strHex = "0" + strHex;
//            }
//            sb.append(strHex);
//        }
//        return sb.toString();
//    }
    Interpret(Method method, boolean logInst, String args) {
        Thread thread = new Thread();
        Frame frame = thread.newFrame(method);
        thread.pushFrame(frame);

        if (null != args) {
            Object jArgs = createArgsArray(method.clazz().loader(), args.split(" "));
            frame.localVars().setRef(0, jArgs);
        }

        //初始化本地方法
        Registry.initNative();

        loop(thread, logInst);
    }
    //创建参数数组
    private Object createArgsArray(ClassLoader loader, String[] args) {
        Class stringClass = loader.loadClass("java/lang/String");
        Object argsArr = stringClass.arrayClass().newArray(args.length);
        Object[] jArgs = argsArr.refs();
        for (int i = 0; i < jArgs.length; i++) {
            jArgs[i] = StringPool.jString(loader, args[i]);
        }
        return argsArr;
    }

    private void loop(Thread thread, boolean logInst) {
        BytecodeReader reader = new BytecodeReader();
        while (true) {
            Frame frame = thread.currentFrame();
            int pc = frame.nextPC();
            thread.setPC(pc);

            reader.reset(frame.method().code, pc);
            byte opcode = reader.readByte();
            Instruction inst = Factory.newInstruction(opcode);
            if (null == inst) {
                System.out.println("Unsupported opcode " + byteToHexString(new byte[]{opcode}));
                break;
            }
            inst.fetchOperands(reader);
            frame.setNextPC(reader.pc());

            if (logInst) {
                logInstruction(frame, inst, opcode);
            }

            //exec
            inst.execute(frame);

            if (thread.isStackEmpty()) {
                break;
            }
        }
    }

    private static void logInstruction(Frame frame, Instruction inst, byte opcode) {
        Method method = frame.method();
        String className = method.clazz().name();
        String methodName = method.name();
        String outStr = (className + "." + methodName + "() \t") +
                "寄存器(指令)：" + byteToHexString(new byte[]{opcode}) +
                " -> " + inst.getClass().getSimpleName();
        System.out.println(outStr);
    }
    //字节码拼接
    private static String byteToHexString(byte[] codes) {
        if (null == codes) return "";
        StringBuilder sb = new StringBuilder();
        sb.append("0x");
        for (byte b : codes) {
            int value = b & 0xFF;
            String strHex = Integer.toHexString(value);
            if (strHex.length() < 2) {
                strHex = "0" + strHex;
            }
            sb.append(strHex);
        }
        return sb.toString();
    }
}
