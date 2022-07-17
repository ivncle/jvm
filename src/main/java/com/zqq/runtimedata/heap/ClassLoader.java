package com.zqq.runtimedata.heap;

import com.zqq.classfile.ClassFile;
import com.zqq.classpath.Classpath;
import com.zqq.runtimedata.heap.constantpoll.AccessFlags;
import com.zqq.runtimedata.heap.constantpoll.RunTimeConstantPool;
import com.zqq.runtimedata.heap.methodarea.*;
import com.zqq.runtimedata.heap.methodarea.Class;

import java.lang.Object;
import java.util.HashMap;
import java.util.Map;

/**
 * 遇到new，getstatic，putstatic，invokestatic这四条字节码指令时；其分别对应Java语法中的使用new关键字实例化对象，读写一个类的静态变量时（若是读的static final 的基本类型或者字符串，那么其值是在字段的ConstantValue属性中就初始化好的，并不需要对类进行初始化），调用类的静态方法时；
 * 使用反射对类进行调用时
 * 初始化一个类的时候，发现其父类还未初始化，那么先触发其父类的初始化
 * 启动虚拟机时，需要制定一个包含main方法的类，虚拟机会先初始化该类
 * 如果一个MethodHandle实例最后的解析结果是 REF_getStatic,REF_putStatic,REF_invokeStatic 的方法的句柄时，会触发对应的 REF 类加载。
 * 加载
 * 验证—-|
 * 准备—-|—>连接
 * 解析—-|
 * 初始化
 * 使用
 * 卸载
 */
/*
class names:
    - primitive types: boolean, byte, int ...
    - primitive arrays: [Z, [B, [I ...
    - non-array classes: java/lang/Object ...
    - array classes: [Ljava/lang/Object; ...
*/
public class ClassLoader {
    //类路径
    private Classpath classpath;
    private Map<String, Class> classMap;

    public ClassLoader(Classpath classpath) {
        this.classpath = classpath;
        this.classMap = new HashMap<>();

        this.loadBasicClasses();
        this.loadPrimitiveClasses();
    }
    //基本类的加载
    private void loadBasicClasses() {
        //经过这一步 load 之后,classMap 中就有Class 了，以及 Object 和 Class 所实现的接口
        Class jlClassClass = this.loadClass("java/lang/Class");
        for (Map.Entry<String, Class> entry : this.classMap.entrySet()) {
            //接下来对 classMap 中的每一个 Class 都创建一个 jClass;使用 jlClassClass.NewObject()方法
            // 通过调用 newObject 方法，为每一个 Class 都创建一个元类对象
            Class clazz = entry.getValue();
            if (clazz.jClass == null) {
                clazz.jClass = jlClassClass.newObject();
                clazz.jClass.extra = clazz;
            }
        }
    }
    //加载基本类型的类:void.class;boolean.class;byte.class
    private void loadPrimitiveClasses() {
        for (Map.Entry<String, String> entry : ClassNameHelper.primitiveTypes.entrySet()) {
            loadPrimitiveClass(entry.getKey());
        }
    }
    //加载基本类型,和数组类似,也没有对应的 class 文件,只能在运行时创建;基本类型:无超类,也没有实现任何接口
    /* 针对基本类型的三点说明：
    1. void 和基本类型的类型名字就是：void，int，float 等
    2. 基本类型的类没有超类，也没有实现任何接口
    3. 非基本类型的类对象是通过 ldc 指令加载到操作数栈中的
    */
    private void loadPrimitiveClass(String className) {
        Class clazz = new Class(AccessFlags.ACC_PUBLIC,
                className,
                this,
                true);
        clazz.jClass = this.classMap.get("java/lang/Class").newObject();
        clazz.jClass.extra = clazz;
        this.classMap.put(className, clazz);
    }
    //加载类
    public Class loadClass(String className) {
        Class clazz = classMap.get(className);
        if (null != clazz) return clazz;

        //'['数组标识
        if (className.getBytes()[0] == '[') {
            clazz = loadArrayClass(className);
        } else {
            clazz = loadNonArrayClass(className);
        }
        //为加载到方法区的类设置类对象
        Class jlClazz = this.classMap.get("java/lang/Class");
        if (null != jlClazz && null != clazz) {
            clazz.jClass = jlClazz.newObject();
            clazz.jClass.extra = clazz;
        }

        return clazz;
    }
    //数组类型加载
    private Class loadArrayClass(String className) {
        Class clazz = new Class(AccessFlags.ACC_PUBLIC,
                className,
                this,
                true,
                this.loadClass("java/lang/Object"),
                new Class[]{
                        this.loadClass("java/lang/Cloneable"),
                        this.loadClass("java/io/Serializable")});
        this.classMap.put(className, clazz);
        return clazz;
    }

    private Class loadNonArrayClass(String className) {
        try {
            byte[] data = this.classpath.readClass(className);
            if (null == data) {
                throw new ClassNotFoundException(className);
            }
            Class clazz = defineClass(data);
            link(clazz);
            return clazz;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void link(Class clazz) {
        //检验
        verify(clazz);
        //准备
        prepare(clazz);
    }

    private void prepare(Class clazz) {
        // 计算new一个对象所需的空间,单位是clazz.instanceSlotCount,主要包含了类的非静态成员变量(包含父类的)
        // 但是这里并没有真正的申请空间，只是计算大小，同时为每个非静态变量关联 slotId
        calcInstanceFieldSlotIds(clazz);
        //计算类的静态成员变量所需的空间，不包含父类，同样也只是计算和分配 slotId，不申请空间
        calcStaticFieldSlotIds(clazz);
        // 为静态变量申请空间,注意:这个申请空间的过程,就是将所有的静态变量赋值为0或者null;
        // 如果是 static final 的基本类型或者 String，其值会保存在ConstantValueAttribute属性中
        // 而ConstantValueAttribute属性中保存的值又是在常量池中！
        allocAndInitStaticVars(clazz);
    }

    private void allocAndInitStaticVars(Class clazz) {
        clazz.staticVars = new Slots(clazz.staticSlotCount);
        for (Field field : clazz.fields) {
            if (field.isStatic() && field.isFinal()) {
                initStaticFinalVar(clazz, field);
            }
        }
    }
    //初始化Static Final类型的变量  准备
    private void initStaticFinalVar(Class clazz, Field field) {
        Slots staticVars = clazz.staticVars;
        RunTimeConstantPool constantPool = clazz.runTimeConstantPool;
        int cpIdx = field.constValueIndex();
        int slotId = field.slotId();

        if (cpIdx > 0) {
            switch (field.descriptor()) {
                case "Z":
                case "B":
                case "C":
                case "S":
                case "I":
                    java.lang.Object val = constantPool.getConstants(cpIdx);
                    staticVars.setInt(slotId, (Integer) val);
                    break;
                case "J":
                    staticVars.setLong(slotId, (Long) constantPool.getConstants(cpIdx));
                    break;
                case "F":
                    staticVars.setFloat(slotId, (Float) constantPool.getConstants(cpIdx));
                    break;
                case "D":
                    staticVars.setDouble(slotId, (Double) constantPool.getConstants(cpIdx));
                    break;
                case "Ljava/lang/String;":
                    String goStr = (String) constantPool.getConstants(cpIdx);
                    com.zqq.runtimedata.heap.methodarea.Object jStr = StringPool.jString(clazz.loader(), goStr);
                    staticVars.setRef(slotId, jStr);
                    break;
            }
        }

    }

    private void calcStaticFieldSlotIds(Class clazz) {
        int slotId = 0;
        for (Field field : clazz.fields) {
            if (field.isStatic()) {
                field.slotId = slotId;
                slotId++;
                if (field.isLongOrDouble()) {
                    slotId++;
                }
            }
        }
        clazz.staticSlotCount = slotId;
    }

    private void calcInstanceFieldSlotIds(Class clazz) {
        int slotId = 0;
        if (clazz.superClass != null) {
            slotId = clazz.superClass.instanceSlotCount;
        }
        for (Field field : clazz.fields) {
            if (!field.isStatic()) {
                field.slotId = slotId;
                slotId++;
                if (field.isLongOrDouble()) {
                    slotId++;
                }
            }
        }
        clazz.instanceSlotCount = slotId;
    }

    private void verify(Class clazz) {
        // 校验字节码，尚未实现
    }
    //定义一个类
    private Class defineClass(byte[] data) throws Exception {
        Class clazz = parseClass(data);
        clazz.loader = this;
        resolveSuperClass(clazz);
        resolveInterfaces(clazz);
        this.classMap.put(clazz.name, clazz);
        return clazz;
    }
    //解析接口
    private void resolveInterfaces(Class clazz) throws Exception {
        int interfaceCount = clazz.interfaceNames.length;
        if (interfaceCount > 0) {
            clazz.interfaces = new Class[interfaceCount];
            for (int i = 0; i < interfaceCount; i++) {
                clazz.interfaces[i] = clazz.loader.loadClass(clazz.interfaceNames[i]);
            }
        }
    }
    //解析父类
    private void resolveSuperClass(Class clazz) throws Exception {
        if (!clazz.name.equals("java/lang/Object")) {
            clazz.superClass = clazz.loader.loadClass(clazz.superClassName);
        }
    }
    //把字节数组解析成一个类
    private Class parseClass(byte[] data) {
        ClassFile classFile = new ClassFile(data);
        return new Class(classFile);
    }


}
