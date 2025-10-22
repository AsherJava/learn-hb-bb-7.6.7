/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.file.FileAreaConfig
 */
package nr.single.map.configurations.file.config;

import com.jiuqi.nr.file.FileAreaConfig;

public class SingleFileAreaConfig
implements FileAreaConfig {
    public static final String AREA_NAME = "single";
    public static final String AREA_DESC = "\u7528\u4e8eJIO\u65e7\u6620\u5c04\u65b9\u6848";
    private static final long FILE_MAX_SIZE = 0xC800000L;
    private static final Boolean ENABLE_FAST = false;
    private static final Boolean ENABLE_ENCRYPT = true;
    private static final Boolean ENABLE_RECYCLEBIN = true;

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

    public String getDesc() {
        return AREA_DESC;
    }
}

