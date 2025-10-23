/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.file.config;

import com.jiuqi.nr.file.FileAreaConfig;

public class FileAreaProperties
implements FileAreaConfig {
    private String name;
    private long maxFileSize = 102400L;
    private boolean enableRecycleBin = false;
    private boolean enableEncrypt = false;
    private boolean enableFastDownload = false;
    private boolean hashFileDate = false;

    @Override
    public boolean isHashFileDate() {
        return this.hashFileDate;
    }

    public void setHashFileDate(boolean hashFileDate) {
        this.hashFileDate = hashFileDate;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public long getMaxFileSize() {
        return this.maxFileSize;
    }

    public void setMaxFileSize(long maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    @Override
    public boolean isEnableRecycleBin() {
        return this.enableRecycleBin;
    }

    public void setEnableRecycleBin(boolean enableRecycleBin) {
        this.enableRecycleBin = enableRecycleBin;
    }

    @Override
    public boolean isEnableEncrypt() {
        return this.enableEncrypt;
    }

    public void setEnableEncrypt(boolean enableEncrypt) {
        this.enableEncrypt = enableEncrypt;
    }

    @Override
    public boolean isEnableFastDownload() {
        return this.enableFastDownload;
    }

    public void setEnableFastDownload(boolean enableFastDownload) {
        this.enableFastDownload = enableFastDownload;
    }
}

