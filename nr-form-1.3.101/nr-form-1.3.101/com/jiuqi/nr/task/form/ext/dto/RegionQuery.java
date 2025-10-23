/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.DataRegionKind
 */
package com.jiuqi.nr.task.form.ext.dto;

import com.jiuqi.nr.definition.common.DataRegionKind;

public class RegionQuery {
    private String regionKey;
    private String formKey;
    private DataRegionKind regionKind;

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public DataRegionKind getRegionKind() {
        return this.regionKind;
    }

    public void setRegionKind(DataRegionKind regionKind) {
        this.regionKind = regionKind;
    }
}

