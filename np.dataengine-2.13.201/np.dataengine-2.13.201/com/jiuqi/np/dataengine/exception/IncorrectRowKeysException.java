/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.exception;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.exception.IncorrectQueryException;

public class IncorrectRowKeysException
extends IncorrectQueryException {
    private static final long serialVersionUID = -6357834248137645138L;
    private DimensionValueSet rowKeys;
    private boolean isInsert;
    private String dimName;

    public IncorrectRowKeysException(DimensionValueSet rowKeys, boolean isInsert, String dimName) {
        super(String.format("%s\u6570\u636e\u884c\u65f6\uff0c\u4e1a\u52a1\u4e3b\u952e\u503c\u4e0d\u80fd\u4e3a\u7a7a\uff0c\u4e1a\u52a1\u4e3b\u952e\u4e3a%s\uff0c\u7ef4\u5ea6%s\u503c\u4e3a\u7a7a", isInsert ? "\u63d2\u5165" : "\u66f4\u65b0", rowKeys, dimName));
        this.rowKeys = rowKeys;
    }

    public DimensionValueSet getRowKeys() {
        return this.rowKeys;
    }

    public void setRowKeys(DimensionValueSet rowKeys) {
        this.rowKeys = rowKeys;
    }

    public boolean isInsert() {
        return this.isInsert;
    }

    public void setInsert(boolean isInsert) {
        this.isInsert = isInsert;
    }

    public String getDimName() {
        return this.dimName;
    }

    public void setDimName(String dimName) {
        this.dimName = dimName;
    }
}

