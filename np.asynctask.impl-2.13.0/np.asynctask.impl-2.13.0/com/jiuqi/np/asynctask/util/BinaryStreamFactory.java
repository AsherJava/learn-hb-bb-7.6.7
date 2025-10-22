/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.asynctask.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BinaryStreamFactory {
    private static final Logger logger = LoggerFactory.getLogger(BinaryStreamFactory.class);
    private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    private byte[] buffer = new byte[1024];

    public byte[] read(InputStream input) throws IOException {
        int len;
        this.byteArrayOutputStream.reset();
        while ((len = input.read(this.buffer)) > -1) {
            this.byteArrayOutputStream.write(this.buffer, 0, len);
        }
        this.byteArrayOutputStream.flush();
        this.close(input);
        return this.byteArrayOutputStream.toByteArray();
    }

    public void closeStream() {
        if (null != this.byteArrayOutputStream) {
            try {
                this.byteArrayOutputStream.close();
            }
            catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private void close(InputStream input) throws IOException {
        if (input != null) {
            input.close();
        }
    }
}

