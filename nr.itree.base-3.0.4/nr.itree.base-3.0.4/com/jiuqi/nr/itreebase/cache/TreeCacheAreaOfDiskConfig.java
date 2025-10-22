/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.file.FileAreaConfig
 */
package com.jiuqi.nr.itreebase.cache;

import com.jiuqi.nr.file.FileAreaConfig;
import org.springframework.stereotype.Component;

@Component
public class TreeCacheAreaOfDiskConfig
implements FileAreaConfig {
    public static final String AREA_NAME = "dim_tree_cache";
    private static final long MAX_FILE_SIZE = 0xC800000L;
    private static final long NAME_OF_FILTER_CACHE_SET_TTL_TIME = 1800L;

    public String getName() {
        return AREA_NAME;
    }

    public long getMaxFileSize() {
        return 0xC800000L;
    }

    public boolean isEnableFastDownload() {
        return true;
    }

    public long getExpirationTime() {
        return 1800L;
    }
}

