/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dafafill.model;

import com.jiuqi.nr.dafafill.model.enums.ExportType;
import java.io.Serializable;

public class ExportInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private ExportType exportType;
    private boolean onlyStyle;

    public ExportType getExportType() {
        return this.exportType;
    }

    public void setExportType(ExportType exportType) {
        this.exportType = exportType;
    }

    public boolean isOnlyStyle() {
        return this.onlyStyle;
    }

    public void setOnlyStyle(boolean onlyStyle) {
        this.onlyStyle = onlyStyle;
    }
}

