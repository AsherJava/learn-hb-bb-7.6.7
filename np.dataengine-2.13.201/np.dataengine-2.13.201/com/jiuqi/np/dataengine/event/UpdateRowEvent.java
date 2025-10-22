/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.event;

import com.jiuqi.np.dataengine.event.RowEvent;
import com.jiuqi.np.dataengine.intf.IDataRow;
import java.util.List;

public class UpdateRowEvent
extends RowEvent {
    private List<IDataRow> updateRows;
    private boolean isBreak;
    private String message;

    public List<IDataRow> getUpdateRows() {
        return this.updateRows;
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

    public void setUpdateRows(List<IDataRow> updateRows) {
        this.updateRows = updateRows;
    }
}

