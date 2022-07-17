package com.zqq.runtimedata.heap.methodarea;

import com.zqq.runtimedata.heap.ClassLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * 这里用来模拟 JVM 中的字符串池，但是由于当前的 JVM 本身就是用 Java 写的，所以会省掉很多真正的细节
 * 这里用一个 HasMap 来模拟字符串常量池，key 为从 class 文件中读到的字符串，value 为我们定义的 Object
 */
public class StringPool {

    private static Map<String, Object> internedStrs = new HashMap<>();
    //通过string获取jvm中的object
    public static Object jString(ClassLoader loader, String goStr) {
        Object internedStr = internedStrs.get(goStr);
        if (null != internedStr) return internedStr;

        char[] chars = goStr.toCharArray();
        Object jChars = new Object(loader.loadClass("[C"), chars);

        Object jStr = loader.loadClass("java/lang/String").newObject();
        jStr.setRefVal("value", "[C", jChars);

        internedStrs.put(goStr, jStr);
        return jStr;
    }
    //把字符串代表的jvm中的object转换成String类型
    public static String goString(Object jStr) {
        Object charArr = jStr.getRefVar("value", "[C");
        return new String(charArr.chars());
    }
    //将jvm中的object类型所表示的String加入字符串常量池
    public static Object internString(Object jStr) {
        String goStr = goString(jStr);
        Object internedStr = internedStrs.get(goStr);
        if (null != internedStr) return internedStr;

        internedStrs.put(goStr, jStr);
        return jStr;
    }

}
