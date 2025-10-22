/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.dataentry.bean.ImportResultItem;
import java.io.Serializable;

public class AbstractImportResultItem
implements ImportResultItem,
Serializable {
    private static final long serialVersionUID = -1658380369663745004L;
    private String errorInfo;

    @Override
    public String getErrorInfo() {
        return this.errorInfo;
    }

    @Override
    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }
}

