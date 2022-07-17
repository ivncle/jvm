package com.zqq.classpath;

import com.zqq.classpath.impl.CompositeEntry;
import com.zqq.classpath.impl.DirEntry;
import com.zqq.classpath.impl.WildcardEntry;
import com.zqq.classpath.impl.ZipEntry;

import java.io.File;
import java.io.IOException;

public interface Entry {

    byte[] readClass(String className) throws IOException;

    static Entry create(String path) {

        //File.pathSeparator；路径分隔符(win\linux)
        if (path.contains(File.pathSeparator)) {
            return new CompositeEntry(path);
        }

        if (path.endsWith("*")) {
            return new WildcardEntry(path);
        }

        if (path.endsWith(".jar") || path.endsWith(".JAR") ||
                path.endsWith(".zip") || path.endsWith(".ZIP")) {
            return new ZipEntry(path);
        }

        return new DirEntry(path);
    }

}
