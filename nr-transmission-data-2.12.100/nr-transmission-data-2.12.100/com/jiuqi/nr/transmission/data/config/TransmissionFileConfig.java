/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.file.FileAreaConfig
 */
package com.jiuqi.nr.transmission.data.config;

import com.jiuqi.nr.file.FileAreaConfig;
import org.springframework.stereotype.Component;

@Component
public class TransmissionFileConfig
implements FileAreaConfig {
    public static final String AREA_NAME = "TRANSMISSION";
    private static final long FILE_MAX_SIZE = 0xC800000L;
    private static final Boolean ENABLE_FAST = false;
    private static final Boolean ENABLE_ENCRYPT = true;
    private static final Boolean ENABLE_RECYCLEBIN = true;
    private static final long EXPIRATION_TIME = 259200L;

    public String getName() {
        return AREA_NAME;
    }

    public long getMaxFileSize() {
        return 0xC800000L;
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
        return 259200L;
    }

    public String getDesc() {
        return "\u591a\u7ea7\u90e8\u7f72-\u4e34\u65f6\u6587\u4ef6";
    }
}

