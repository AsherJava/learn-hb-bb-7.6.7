/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.complier;

import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.Map;
import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;

class MemoryJavaFileManager
extends ForwardingJavaFileManager<JavaFileManager> {
    final Map<String, byte[]> classBytes = new HashMap<String, byte[]>();

    MemoryJavaFileManager(JavaFileManager fileManager) {
        super(fileManager);
    }

    public Map<String, byte[]> getClassBytes() {
        return new HashMap<String, byte[]>(this.classBytes);
    }

    @Override
    public void flush() throws IOException {
    }

    @Override
    public void close() throws IOException {
        this.classBytes.clear();
    }

    @Override
    public JavaFileObject getJavaFileForOutput(JavaFileManager.Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
        if (kind == JavaFileObject.Kind.CLASS) {
            return new MemoryOutputJavaFileObject(className);
        }
        return super.getJavaFileForOutput(location, className, kind, sibling);
    }

    JavaFileObject makeStringSource(String name, String code) {
        return new MemoryInputJavaFileObject(name, code);
    }

    class MemoryOutputJavaFileObject
    extends SimpleJavaFileObject {
        final String name;

        MemoryOutputJavaFileObject(String name) {
            super(URI.create("string:///" + name), JavaFileObject.Kind.CLASS);
            this.name = name;
        }

        @Override
        public OutputStream openOutputStream() {
            return new FilterOutputStream(new ByteArrayOutputStream()){

                @Override
                public void close() throws IOException {
                    this.out.close();
                    ByteArrayOutputStream bos = (ByteArrayOutputStream)this.out;
                    MemoryJavaFileManager.this.classBytes.put(MemoryOutputJavaFileObject.this.name, bos.toByteArray());
                }
            };
        }
    }

    static class MemoryInputJavaFileObject
    extends SimpleJavaFileObject {
        final String code;

        MemoryInputJavaFileObject(String name, String code) {
            super(URI.create("string:///" + name), JavaFileObject.Kind.SOURCE);
            this.code = code;
        }

        @Override
        public CharBuffer getCharContent(boolean ignoreEncodingErrors) {
            return CharBuffer.wrap(this.code);
        }
    }
}

