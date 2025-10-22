/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.param;

import com.jiuqi.nr.data.excel.extend.export.cellstyle.CustomCellStyleProvider;
import com.jiuqi.nr.data.excel.obj.ExportOps;

public class BaseExpPar {
    private String formSchemeKey;
    private ExportOps ops;
    private CustomCellStyleProvider customCellStyleProvider;

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public ExportOps getOps() {
        return this.ops;
    }

    public void setOps(ExportOps ops) {
        this.ops = ops;
    }

    public CustomCellStyleProvider getCustomCellStyleProvider() {
        return this.customCellStyleProvider;
    }

    public void setCustomCellStyleProvider(CustomCellStyleProvider customCellStyleProvider) {
        this.customCellStyleProvider = customCellStyleProvider;
    }
}

