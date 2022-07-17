package com.zqq.classfile;

import com.zqq.classfile.attributes.AttributeInfo;
import com.zqq.classfile.attributes.impl.SourceFileAttribute;
import com.zqq.classfile.constantpool.ConstantPool;

/**
 * ClassFile {
 *   u4 magic;	//魔数
 *   u2 minor_version;	//次版本号
 *   u2 major_version;	//主版本号
 *   u2 constant_pool_count;	//常量池大小
 *   cp_info constant_pool[constant_pool_count-1]; //常量池
 *   u2 access_flags;	//类访问标志,表明 class 文件定义的是类还是接口，访问级别是 public 还是 private，等
 *   u2 this_class;	//
 *   u2 super_class;	//
 *   u2 interfaces_count;	//本类实现的接口数量
 *   u2 interfaces[interfaces_count];	//实现的接口,存放在数组中
 *   u2 fields_count;		//本类中含有字段数
 *   field_info fields[fields_count];	//数组中存放这各个字段
 *   u2 methods_count;		//本类中含有的方法数
 *   method_info methods[methods_count];	//数组中存放着各个方法
 *   u2 attributes_count;			//本类中含有的属性数量;
 *   attribute_info attributes[attributes_count];	//数组中存放着各个属性
 * }
 */
public class ClassFile {
    //次版本号
    private int minorVersion;
    //主版本号
    private int majorVersion;
    //常量池
    private ConstantPool constantPool;
    //访问权限
    private int accessFlags;
    //类名称索引
    private int thisClassIdx;
    //父类索引
    private int supperClassIdx;
    //接口索引
    private int[] interfaces;
    //字段表
    private MemberInfo[] fields;
    //方法表
    private MemberInfo[] methods;
    //属性表
    private AttributeInfo[] attributes;

    public ClassFile(byte[] classData) {
        ClassReader reader = new ClassReader(classData);
        this.readAndCheckMagic(reader);
        this.readAndCheckVersion(reader);
        this.constantPool = this.readConstantPool(reader);
        this.accessFlags = reader.readUint16();
        this.thisClassIdx = reader.readUint16();
        this.supperClassIdx = reader.readUint16();
        this.interfaces = reader.readUint16s();
        this.fields = MemberInfo.readMembers(reader, constantPool);
        this.methods = MemberInfo.readMembers(reader, constantPool);
        this.attributes = AttributeInfo.readAttributes(reader, constantPool);
    }
    //魔数检查
    private void readAndCheckMagic(ClassReader reader) {
        long magic = reader.readUint32();
        if (magic != (0xCAFEBABE & 0x0FFFFFFFFL)) {
            throw new ClassFormatError("magic!");
        }
    }
    //版本号检查
    private void readAndCheckVersion(ClassReader reader) {
        this.minorVersion = reader.readUint16();
        this.majorVersion = reader.readUint16();
        switch (this.majorVersion) {
            case 45:
                return;
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
                if (this.minorVersion == 0)
                    return;
        }
        throw new UnsupportedClassVersionError();
    }

    private ConstantPool readConstantPool(ClassReader reader) {
        return new ConstantPool(reader);
    }
    //读取次版本号
    public int minorVersion() {
        return this.minorVersion;
    }
    //读取主版本号
    public int majorVersion() {
        return this.majorVersion;
    }
    //读取常量池
    public ConstantPool constantPool() {
        return this.constantPool;
    }
    //读取访问权限
    public int accessFlags() {
        return this.accessFlags;
    }
    //读取字段表
    public MemberInfo[] fields() {
        return this.fields;
    }
    //读取方法表
    public MemberInfo[] methods() {
        return this.methods;
    }
    //读取类名
    public String className() {
        return this.constantPool.getClassName(this.thisClassIdx);
    }
    //读取父类名
    public String superClassName() {
        if (this.supperClassIdx <= 0) return "";
        return this.constantPool.getClassName(this.supperClassIdx);
    }
    //读取接口名
    public String[] interfaceNames() {
        String[] interfaceNames = new String[this.interfaces.length];
        for (int i = 0; i < this.interfaces.length; i++) {
            interfaceNames[i] = this.constantPool.getClassName(interfaces[i]);
        }
        return interfaceNames;
    }

    public SourceFileAttribute sourceFileAttribute() {
        for (AttributeInfo arrInfo : this.attributes) {
            if (arrInfo instanceof SourceFileAttribute){
                return (SourceFileAttribute) arrInfo;
            }
        }
        return null;
    }

}
