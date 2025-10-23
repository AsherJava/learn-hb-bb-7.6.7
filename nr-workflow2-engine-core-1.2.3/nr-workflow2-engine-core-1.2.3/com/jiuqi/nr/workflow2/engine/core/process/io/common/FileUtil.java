/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.process.io.common;

import com.jiuqi.nr.workflow2.engine.core.exception.ProcessIOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {
    public static void forceCreateNewFile(File file) {
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        }
        catch (IOException e) {
            throw new ProcessIOException(null, "\u521b\u5efa\u6587\u4ef6\u5931\u8d25\uff1a" + file.getName(), e);
        }
    }

    public static OutputStream createFileOutputStream(File file) {
        try {
            return new FileOutputStream(file);
        }
        catch (IOException e) {
            throw new ProcessIOException(null, "\u6253\u5f00\u6587\u4ef6\u5931\u8d25\uff1a" + file.getName(), e);
        }
    }

    public static FileWriter createFileWriter(File file) {
        try {
            return new FileWriter(file);
        }
        catch (IOException e) {
            throw new ProcessIOException(null, "\u6253\u5f00\u6587\u4ef6\u5931\u8d25\uff1a" + file.getName(), e);
        }
    }

    public static InputStream createFileInputStream(File file) {
        try {
            return new FileInputStream(file);
        }
        catch (IOException e) {
            throw new ProcessIOException(null, "\u6253\u5f00\u6587\u4ef6\u5931\u8d25\uff1a" + file.getName(), e);
        }
    }

    public static FileReader createFileReader(File file) {
        try {
            return new FileReader(file);
        }
        catch (IOException e) {
            throw new ProcessIOException(null, "\u6253\u5f00\u6587\u4ef6\u5931\u8d25\uff1a" + file.getName(), e);
        }
    }
}

