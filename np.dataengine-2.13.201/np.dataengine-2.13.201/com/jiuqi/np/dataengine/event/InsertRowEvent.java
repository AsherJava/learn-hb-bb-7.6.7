/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.event;

import com.jiuqi.np.dataengine.event.RowEvent;
import com.jiuqi.np.dataengine.intf.IDataRow;
import java.util.List;

public class InsertRowEvent
extends RowEvent {
    private List<IDataRow> insertRows;
    private boolean isBreak;
    private String message;

    public List<IDataRow> getInsertRows() {
        return this.insertRows;
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

    public void setInsertRows(List<IDataRow> insertRows) {
        this.insertRows = insertRows;
    }
}

