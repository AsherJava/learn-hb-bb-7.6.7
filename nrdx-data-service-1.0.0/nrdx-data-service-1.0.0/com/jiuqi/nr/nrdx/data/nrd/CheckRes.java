/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.nrdx.data.nrd;

import java.io.File;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckRes
implements AutoCloseable {
    private boolean nrd;
    private int encryptType;
    public static final int ENCRYPT_NONE = 0;
    public static final int ENCRYPT = 1;
    public static final int UNABLE_DECRYPT = 2;
    private InputStream originalData;
    private File tmpFile;
    private static final Logger log = LoggerFactory.getLogger(CheckRes.class);

    public boolean isNrd() {
        return this.nrd;
    }

    public void setNrd(boolean nrd) {
        this.nrd = nrd;
    }

    public InputStream getOriginalData() {
        return this.originalData;
    }

    public void setOriginalData(InputStream originalData) {
        this.originalData = originalData;
    }

    public void setTmpFile(File tmpFile) {
        this.tmpFile = tmpFile;
    }

    public int getEncryptType() {
        return this.encryptType;
    }

    public void setEncryptType(int encryptType) {
        this.encryptType = encryptType;
    }

    @Override
    public void close() throws Exception {
        boolean delete;
        if (this.originalData != null) {
            try {
                this.originalData.close();
            }
            catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        if (this.tmpFile != null && !(delete = this.tmpFile.delete())) {
            log.error("{}\u4e34\u65f6\u6587\u4ef6\u5220\u9664\u5931\u8d25", (Object)this.tmpFile.getAbsolutePath());
        }
    }
}

