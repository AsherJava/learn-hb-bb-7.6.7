/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.unit.uselector.dataio;

import com.jiuqi.nr.unit.uselector.dataio.IRowData;

public class ExcelRowData
implements IRowData {
    private String code;
    private String title;

    @Override
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code != null ? code.trim() : "";
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title != null ? title.trim() : "";
    }
}

