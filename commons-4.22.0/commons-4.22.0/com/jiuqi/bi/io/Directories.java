/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

public class Directories {
    private Directories() {
    }

    public static void removeAll(File dir) throws IOException {
        if (!dir.exists()) {
            return;
        }
        Directories.removeSubs(dir);
        Files.delete(dir.toPath());
    }

    public static void removeSubs(File dir) throws IOException {
        if (!dir.isDirectory()) {
            throw new IOException("\u5220\u9664\u76ee\u5f55\u4e0d\u5b58\u5728\uff1a" + dir.getPath());
        }
        for (File subFile : dir.listFiles()) {
            if (".".equals(subFile.getName()) || "..".equals(subFile.getName())) continue;
            if (subFile.isDirectory()) {
                Directories.removeAll(subFile);
                continue;
            }
            Files.delete(subFile.toPath());
        }
    }

    public static void removeAll(String dir) throws IOException {
        Directories.removeAll(new File(dir));
    }

    public static long sizeOf(File path) throws FileNotFoundException {
        if (!path.exists()) {
            throw new FileNotFoundException("\u672a\u627e\u5230\u6307\u5b9a\u7684\u6587\u4ef6\u5bf9\u8c61\uff1a" + path);
        }
        if (path.isFile()) {
            return path.length();
        }
        File[] subFiles = path.listFiles();
        if (subFiles == null) {
            return 0L;
        }
        long size = 0L;
        for (File subFile : subFiles) {
            if (Files.isSymbolicLink(subFile.toPath())) continue;
            size += Directories.sizeOf(subFile);
        }
        return size;
    }
}

