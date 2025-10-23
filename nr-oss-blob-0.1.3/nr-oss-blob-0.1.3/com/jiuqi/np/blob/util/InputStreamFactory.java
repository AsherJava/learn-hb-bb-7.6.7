/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.blob.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InputStreamFactory {
    private static final Logger logger = LoggerFactory.getLogger(InputStreamFactory.class);
    private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    private byte[] buffer = new byte[1024];

    public InputStreamFactory(InputStream input) throws IOException {
        int len;
        while ((len = input.read(this.buffer)) > -1) {
            this.byteArrayOutputStream.write(this.buffer, 0, len);
        }
        this.byteArrayOutputStream.flush();
    }

    public InputStream newInputStream() {
        return new ByteArrayInputStream(this.byteArrayOutputStream.toByteArray());
    }

    public byte[] getBytes() {
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
}

