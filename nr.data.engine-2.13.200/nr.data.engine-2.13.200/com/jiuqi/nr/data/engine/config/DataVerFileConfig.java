/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.file.FileAreaConfig
 */
package com.jiuqi.nr.data.engine.config;

import com.jiuqi.nr.file.FileAreaConfig;
import org.springframework.stereotype.Component;

@Component
public class DataVerFileConfig
implements FileAreaConfig {
    public String AREA_NAME = "DataVer";
    private long MAX_FILE_SIZE = 10240000L;

    public String getName() {
        return this.AREA_NAME;
    }

    public long getMaxFileSize() {
        return this.MAX_FILE_SIZE;
    }

    public void setName(String areaName) {
        this.AREA_NAME = areaName;
    }

    public DataVerFileConfig(String areaName) {
        this.AREA_NAME = areaName;
    }

    public DataVerFileConfig() {
    }
}

