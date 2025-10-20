/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.complier;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

public class MemoryClassLoader
extends URLClassLoader {
    Map<String, byte[]> classBytes = new HashMap<String, byte[]>();

    public MemoryClassLoader(Map<String, byte[]> classBytes) {
        super(new URL[0], MemoryClassLoader.class.getClassLoader());
        this.classBytes.putAll(classBytes);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] buf = this.classBytes.get(name);
        if (buf == null) {
            return super.findClass(name);
        }
        this.classBytes.remove(name);
        return this.defineClass(name, buf, 0, buf.length);
    }
}

