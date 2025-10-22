/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.office.excel2.steam.xlsx.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;

public class TempFileUtil {
    /*
     * Loose catch block
     */
    public static File writeInputStreamToFile(InputStream is, int bufferSize) throws IOException {
        File f = Files.createTempFile("tmp-", ".xlsx", new FileAttribute[0]).toFile();
        try {
            try (FileOutputStream fos = new FileOutputStream(f);){
                int read;
                byte[] bytes = new byte[bufferSize];
                while ((read = is.read(bytes)) != -1) {
                    fos.write(bytes, 0, read);
                }
                File file = f;
                return file;
            }
            {
                catch (Throwable throwable) {
                    throw throwable;
                }
            }
        }
        finally {
            is.close();
        }
    }
}

