/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.file;

import com.jiuqi.nr.file.FileAreaConfig;

public class FileAreaConfigSwitch
implements FileAreaConfig {
    private final FileAreaConfig originalConfig;

    public FileAreaConfigSwitch(FileAreaConfig fileAreaConfig) {
        this.originalConfig = fileAreaConfig;
    }

    @Override
    public String getName() {
        return this.originalConfig.getName();
    }

    @Override
    public long getMaxFileSize() {
        return Long.MAX_VALUE;
    }

    @Override
    public long getExpirationTime() {
        return this.originalConfig.getExpirationTime();
    }

    @Override
    public boolean isEnableRecycleBin() {
        return false;
    }

    @Override
    public boolean isEnableEncrypt() {
        return false;
    }

    @Override
    public boolean isEnableFastDownload() {
        return this.originalConfig.isEnableFastDownload();
    }

    @Override
    public boolean isHashFileDate() {
        return this.originalConfig.isHashFileDate();
    }
}

