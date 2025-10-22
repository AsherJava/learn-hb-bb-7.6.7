/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.file.FileAreaConfig
 */
package com.jiuqi.nr.designer.web.facade;

import com.jiuqi.nr.file.FileAreaConfig;

public class AttachmentFieleConfig
implements FileAreaConfig {
    private String areaName = "";
    private static long MAX_FILE_SIZE = 10240000L;

    public String getName() {
        return this.areaName;
    }

    public long getMaxFileSize() {
        return MAX_FILE_SIZE;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getDesc() {
        return "\u9644\u4ef6\u6307\u6807\u9ed8\u8ba4\u6a21\u677f-\u4e0a\u4f20\u6587\u4ef6";
    }
}

