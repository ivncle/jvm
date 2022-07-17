package com.zqq;

import com.zqq.classfile.ClassFile;
import com.zqq.classfile.MemberInfo;
import com.zqq.classfile.attributes.AttributeInfo;
import com.zqq.classpath.Classpath;
import com.zqq.runtimedata.Frame;
import com.zqq.runtimedata.LocalVars;
import com.zqq.runtimedata.OperandStack;
import com.zqq.runtimedata.heap.ClassLoader;
import com.zqq.runtimedata.heap.methodarea.Class;
import com.zqq.runtimedata.heap.methodarea.Method;

import java.util.Arrays;

/**
 * 参考资料：《JVM规范》 《深入理解Java虚拟机》  《自己动手写Java虚拟机》。。。。。。
 */
public class Main {
    public static void main(String[] args) {
        Cmd cmd = Cmd.parse(args);
        if (!cmd.ok || cmd.helpFlag) {
            System.out.println("Usage: <main class> [-options] class [args...]");
            return;
        }
        if (cmd.versionFlag) {
            System.out.println("java version \"1.8.0\"");
            return;
        }
        startJVM(cmd);
    }

    //    private static void startJVM(Cmd cmd) {
//        Classpath cp = new Classpath(cmd.jre, cmd.classpath);
//        //System.out.println(cmd.classpath);
//        System.out.printf("classpath：%s class：%s args：%s\n", cp, cmd.getMainClass(), cmd.getAppArgs());
//        //获取className
//        String className = cmd.getMainClass().replace(".", "/");
//        try {
//            byte[] classData = cp.readClass(className);
//            System.out.println("classData：");
//            int count = 0;
//            for (byte b : classData) {
//                //16进制输出
//                System.out.print(String.format("%02x", b & 0xff) + " ");
//                count++;
//                if (count == 16) {
//                    count = 0;
//                    System.out.println();
//                }
//            }
//        } catch (Exception e) {
//            System.out.println("Could not find or load main class " + cmd.getMainClass());
//            e.printStackTrace();
//        }
//    }

    //    private static void startJVM(Cmd cmd) {
//        Classpath classpath = new Classpath(cmd.jre, cmd.classpath);
//        System.out.printf("classpath：%s class：%s args：%s\n",
//                classpath, cmd.getMainClass(), cmd.getAppArgs());
//        //获取className
//        String className = cmd.getMainClass().replace(".", "/");
//        ClassFile classFile = loadClass(className, classpath);
//        assert classFile != null;
//        printClassInfo(classFile);
//    }
//
//    private static ClassFile loadClass(String className, Classpath classpath) {
//        try {
//            byte[] classData = classpath.readClass(className);
//            return new ClassFile(classData);
//        } catch (Exception e) {
//            System.out.println("Could not find or load main class " + className);
//            return null;
//        }
//    }
//    private static void printClassInfo(ClassFile cf) {
//        System.out.println("version: " + cf.majorVersion() + "." + cf.minorVersion());
//        System.out.println("constants count：" + cf.constantPool().getSiz());
//        System.out.format("access flags：0x%x\n", cf.accessFlags());
//        System.out.println("this class：" + cf.className());
//        System.out.println("super class：" + cf.superClassName());
//        System.out.println("interfaces：" + Arrays.toString(cf.interfaceNames()));
//
//        System.out.println("fields count：" + cf.fields().length);
//        for (MemberInfo memberInfo : cf.fields()) {
//            System.out.format("%s \t\t %s\n", memberInfo.name(), memberInfo.descriptor());
//        }
//
//        System.out.println("methods count: " + cf.methods().length);
//        for (MemberInfo memberInfo : cf.methods()) {
//            System.out.format("%s \t\t %s\n", memberInfo.name(), memberInfo.descriptor());
//        }
//
//    }

//    private static void startJVM(Cmd args) {
//        Frame frame = new Frame(100, 100);
//        test_localVars(frame.localVars());
//        test_operandStack(frame.operandStack());
//    }
//
//    private static void test_localVars(LocalVars vars) {
//        vars.setInt(0, 100);
//        vars.setInt(1, -100);
//        System.out.println(vars.getInt(0));
//        System.out.println(vars.getInt(1));
//    }
//
//    private static void test_operandStack(OperandStack ops) {
//        ops.pushInt(100);
//        ops.pushInt(-100);
//        ops.pushRef(null);
//        System.out.println(ops.popRef());
//        System.out.println(ops.popInt());
//    }

//    private static void startJVM(Cmd cmd) {
//        Classpath classpath = new Classpath(cmd.jre, cmd.classpath);
//        System.out.printf("classpath:%s class:%s args:%s\n", classpath, cmd.getMainClass(), cmd.getAppArgs());
//        String className = cmd.getMainClass().replace(".", "/");
//        ClassFile classFile = loadClass(className, classpath);
//        MemberInfo mainMethod = getMainMethod(classFile);
//        if (null == mainMethod) {
//            System.out.println("Main method not found in class " + cmd.classpath);
//            return;
//        }
//        new Interpret(mainMethod);
//    }
//
//    private static ClassFile loadClass(String className, Classpath cp) {
//        try {
//            byte[] classData = cp.readClass(className);
//            return new ClassFile(classData);
//        } catch (Exception e) {
//            System.out.println("Could not find or load main class " + className);
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    //找到主函数入口方法
//    private static MemberInfo getMainMethod(ClassFile cf) {
//        if (null == cf) return null;
//        MemberInfo[] methods = cf.methods();
//        for (MemberInfo m : methods) {
//            if ("main".equals(m.name()) && "([Ljava/lang/String;)V".equals(m.descriptor())) {
//                return m;
//            }
//        }
//        return null;
//    }

    //    private static void startJVM(Cmd cmd) {
//        Classpath classpath = new Classpath(cmd.jre, cmd.classpath);
//        ClassLoader classLoader = new ClassLoader(classpath);
//        //获取className
//        String className = cmd.getMainClass().replace(".", "/");
//        Class mainClass = classLoader.loadClass(className);
//        Method mainMethod = mainClass.getMainMethod();
//        if (null == mainMethod) {
//            throw new RuntimeException("Main method not found in class " + cmd.getMainClass());
//        }
//        new Interpret(mainMethod, cmd.verboseClassFlag);
//    }
    private static void startJVM(Cmd cmd) {
        Classpath classpath = new Classpath(cmd.jre, cmd.classpath);
        ClassLoader classLoader = new ClassLoader(classpath);
        //获取className
        String className = cmd.getMainClass().replace(".", "/");
        Class mainClass = classLoader.loadClass(className);
        Method mainMethod = mainClass.getMainMethod();
        if (null == mainMethod) {
            throw new RuntimeException("Main method not found in class " + cmd.getMainClass());
        }
        new Interpret(mainMethod, cmd.verboseClassFlag, cmd.args);
    }
}
