/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.oss.storage;

import com.jiuqi.bi.oss.DigestUtils;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectDataInputStream
extends FilterInputStream {
    private String md5;
    private int size;
    private MessageDigest digest;
    private Logger logger = LoggerFactory.getLogger(ObjectDataInputStream.class);

    public ObjectDataInputStream(InputStream in) {
        super(in);
        try {
            this.digest = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e) {
            this.logger.error(e.getMessage(), e);
        }
    }

    public void append(InputStream in) throws IOException {
        if (this.in != null) {
            this.in.close();
        }
        this.in = in;
    }

    public String getMd5() {
        if (this.md5 == null) {
            this.md5 = DigestUtils.encodeHexString(this.digest.digest());
        }
        return this.md5;
    }

    public int getSize() {
        return this.size;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int s = super.read(b, off, len);
        if (s > 0) {
            this.digest.update(b, 0, s);
            this.size += s;
        }
        return s;
    }

    @Override
    public void close() throws IOException {
        super.close();
    }
}

