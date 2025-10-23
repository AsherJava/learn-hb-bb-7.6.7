/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.web.facade;

import com.jiuqi.nr.datascheme.web.facade.BaseDataVO;

public class DataGroupVO
extends BaseDataVO {
    private String dataSchemeKey;
    private String parentKey;
    private boolean schemeGroup;

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public String getParentKey() {
        return this.parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public boolean isSchemeGroup() {
        return this.schemeGroup;
    }

    public void setSchemeGroup(boolean schemeGroup) {
        this.schemeGroup = schemeGroup;
    }
}

