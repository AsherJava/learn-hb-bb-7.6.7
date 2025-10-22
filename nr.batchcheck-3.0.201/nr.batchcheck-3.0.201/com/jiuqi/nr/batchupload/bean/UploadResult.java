/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.TableKind
 */
package com.jiuqi.nr.batchupload.bean;

import com.jiuqi.np.definition.common.TableKind;

public class UploadResult {
    private String key;
    private String title;
    private TableKind kind;
    private String dimensionName;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TableKind getKind() {
        return this.kind;
    }

    public void setKind(TableKind kind) {
        this.kind = kind;
    }

    public String getDimensionName() {
        return this.dimensionName;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }
}

