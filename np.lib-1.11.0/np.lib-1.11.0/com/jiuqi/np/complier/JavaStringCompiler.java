/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.complier;

import com.jiuqi.np.complier.MemoryClassLoader;
import com.jiuqi.np.complier.MemoryJavaFileManager;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class JavaStringCompiler {
    JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    StandardJavaFileManager stdManager = this.compiler.getStandardFileManager(null, null, null);

    public Map<String, byte[]> compile(String fileName, String source) throws IOException {
        try (MemoryJavaFileManager manager = new MemoryJavaFileManager(this.stdManager);){
            JavaFileObject javaFileObject = manager.makeStringSource(fileName, source);
            JavaCompiler.CompilationTask task = this.compiler.getTask(null, manager, null, null, null, Arrays.asList(javaFileObject));
            Boolean result = task.call();
            if (result == null || !result.booleanValue()) {
                throw new RuntimeException("Compilation failed.");
            }
            Map<String, byte[]> map = manager.getClassBytes();
            return map;
        }
    }

    public Class<?> loadClass(String name, Map<String, byte[]> classBytes) throws ClassNotFoundException, IOException {
        try (MemoryClassLoader classLoader = new MemoryClassLoader(classBytes);){
            Class<?> clazz = classLoader.loadClass(name);
            return clazz;
        }
    }
}

