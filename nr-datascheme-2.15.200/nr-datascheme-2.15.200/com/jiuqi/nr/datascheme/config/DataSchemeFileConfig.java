/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.file.FileAreaConfig
 */
package com.jiuqi.nr.datascheme.config;

import com.jiuqi.nr.file.FileAreaConfig;

public class DataSchemeFileConfig
implements FileAreaConfig {
    public static final String AREA_NAME = "DATASCHEME";
    private static final long FILE_MAX_SIZE = 0xA00000L;
    private static final Boolean ENABLE_FAST = false;
    private static final Boolean ENABLE_ENCRYPT = false;
    private static final Boolean ENABLE_RECYCLEBIN = false;
    private static final long EXPIRATION_TIME = 10800L;

    public String getName() {
        return AREA_NAME;
    }

    public long getMaxFileSize() {
        return 0xA00000L;
    }

    public boolean isEnableRecycleBin() {
        return ENABLE_RECYCLEBIN;
    }

    public boolean isEnableEncrypt() {
        return ENABLE_ENCRYPT;
    }

    public boolean isEnableFastDownload() {
        return ENABLE_FAST;
    }

    public long getExpirationTime() {
        return 10800L;
    }

    public String getDesc() {
        return "\u6570\u636e\u65b9\u6848-\u5bfc\u5165\u6570\u636e\u6307\u6807";
    }
}

