/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.paramInfo;

import com.jiuqi.nr.dataentry.paramInfo.BatchQueryUpload;
import java.io.Serializable;

public class ExportExcelState
extends BatchQueryUpload
implements Serializable {
    private static final long serialVersionUID = 2227318205023472549L;
    public String tableHeader;

    public String getTableHeader() {
        return this.tableHeader;
    }

    public void setTableHeader(String tableHeader) {
        this.tableHeader = tableHeader;
    }
}

