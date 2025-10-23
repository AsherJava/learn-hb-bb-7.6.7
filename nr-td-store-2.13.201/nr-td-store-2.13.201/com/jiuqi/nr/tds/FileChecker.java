/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tds;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;

public class FileChecker {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean isCompressedFileByHeader(File file) {
        if (file == null) return false;
        if (!file.isFile()) {
            return false;
        }
        try (InputStream is = Files.newInputStream(file.toPath(), new OpenOption[0]);){
            byte[] header = new byte[6];
            if (is.read(header) != header.length) {
                boolean bl2 = false;
                return bl2;
            }
            boolean bl = FileChecker.isZip(header) || FileChecker.isRar(header) || FileChecker.is7z(header);
            return bl;
        }
        catch (IOException e) {
            return false;
        }
    }

    private static boolean isZip(byte[] header) {
        return header[0] == 80 && header[1] == 75 && header[2] == 3 && header[3] == 4;
    }

    private static boolean isRar(byte[] header) {
        return header[0] == 82 && header[1] == 97 && header[2] == 114 && header[3] == 33;
    }

    private static boolean is7z(byte[] header) {
        return header[0] == 55 && header[1] == 122 && header[2] == -68 && header[3] == -81;
    }
}

