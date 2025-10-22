/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.event;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.util.List;

public class RevokeRowEvent {
    private String tableKey;
    private List<DimensionValueSet> revokeRows;
    private boolean isBreak;
    private String message;

    public String getTableKey() {
        return this.tableKey;
    }

    public List<DimensionValueSet> getRevokeRows() {
        return this.revokeRows;
    }

    public void setBreak(String message) {
        this.isBreak = true;
        this.message = message;
    }

    public boolean isBreak() {
        return this.isBreak;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTableKey(String tableKey) {
        this.tableKey = tableKey;
    }

    public void setRevokeRows(List<DimensionValueSet> revokeRows) {
        this.revokeRows = revokeRows;
    }
}

