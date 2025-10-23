/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.file;

public interface FileAreaConfig {
    public static final String DEFAULT_FILE_AREA_NAME = "default";
    public static final long DEFAULT_MAX_FILE_SIZE = 102400L;
    public static final boolean DEFAULT_ENABLE_RECYCLEBIN = false;
    public static final boolean DEFAULT_ENABLE_ENCRYPT = false;
    public static final boolean DEFAULT_ENABLE_FAST_DOWNLOAD = false;
    public static final boolean DEFAULT_HASH_FILE_DATE = false;
    public static final long DEFAULT_EXPIRATION_TIME = 86400L;

    public String getName();

    @Deprecated
    default public long getMaxFileSize() {
        return 102400L;
    }

    default public long getExpirationTime() {
        return 86400L;
    }

    @Deprecated
    default public boolean isEnableRecycleBin() {
        return false;
    }

    @Deprecated
    default public boolean isEnableEncrypt() {
        return false;
    }

    default public boolean isEnableFastDownload() {
        return false;
    }

    default public boolean isHashFileDate() {
        return false;
    }

    default public String getDesc() {
        return null;
    }
}

