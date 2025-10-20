/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.oss.storage;

import com.jiuqi.bi.oss.storage.StorageUtils;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class TemporaryFileInputStream
extends FilterInputStream {
    private File file;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public TemporaryFileInputStream(String key, InputStream input) throws IOException {
        super(null);
        this.file = StorageUtils.createTemporaryFile(key, System.currentTimeMillis());
        FileOutputStream fos = new FileOutputStream(this.file);
        try (BufferedOutputStream bos = new BufferedOutputStream(fos);){
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = input.read(buffer, 0, 1024)) != -1) {
                bos.write(buffer, 0, len);
            }
            bos.flush();
        }
        this.in = new FileInputStream(this.file);
    }

    public TemporaryFileInputStream(File tmpfile) throws IOException {
        super(null);
        this.file = tmpfile;
        this.in = new FileInputStream(this.file);
    }

    @Override
    public void close() throws IOException {
        super.close();
        this.file.delete();
    }
}

