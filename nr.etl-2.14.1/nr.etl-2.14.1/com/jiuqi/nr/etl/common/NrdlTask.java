/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.etl.common;

import com.jiuqi.nr.etl.common.UniversalTask;

public class NrdlTask
extends UniversalTask {
    private String parentGuid;
    private boolean isFolder;

    public String getParentGuid() {
        return this.parentGuid;
    }

    public void setParentGuid(String parentGuid) {
        this.parentGuid = parentGuid;
    }

    public boolean getIsFolder() {
        return this.isFolder;
    }

    public void setIsFolder(boolean isFolder) {
        this.isFolder = isFolder;
    }
}

