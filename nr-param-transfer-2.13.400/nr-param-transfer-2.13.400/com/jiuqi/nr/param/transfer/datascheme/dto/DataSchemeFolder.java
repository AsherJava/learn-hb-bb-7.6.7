/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.FolderNode
 *  com.jiuqi.nr.datascheme.api.type.DataGroupKind
 */
package com.jiuqi.nr.param.transfer.datascheme.dto;

import com.jiuqi.bi.transfer.engine.FolderNode;
import com.jiuqi.nr.datascheme.api.type.DataGroupKind;
import java.time.Instant;

public class DataSchemeFolder
extends FolderNode {
    private String dataSchemeKey;
    private DataGroupKind dataGroupKind;
    private String desc;
    private Instant updateTime;

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public DataGroupKind getDataGroupKind() {
        return this.dataGroupKind;
    }

    public void setDataGroupKind(DataGroupKind dataGroupKind) {
        this.dataGroupKind = dataGroupKind;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Instant getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }
}

