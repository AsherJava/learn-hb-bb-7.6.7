/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.update;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.update.UpdateDataColumn;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UpdateDataRecord
implements Serializable {
    private static final long serialVersionUID = -7614634404762049256L;
    private Map<String, UpdateDataColumn> updateColumns = new HashMap<String, UpdateDataColumn>();
    private DimensionValueSet rowkeys;

    public Collection<UpdateDataColumn> getUpdateColumns() {
        return this.updateColumns.values();
    }

    public void addData(String name, int type, int scale, Object value, Object oldValue) {
        UpdateDataColumn c = this.updateColumns.get(name);
        if (c != null) {
            c.setValue(value);
        } else {
            UpdateDataColumn column = new UpdateDataColumn(name, type, scale, value, oldValue);
            this.updateColumns.put(name, column);
        }
    }

    public void addData(String name, int type, Object value, Object oldValue) {
        this.addData(name, type, Integer.MIN_VALUE, value, oldValue);
    }

    public DimensionValueSet getRowkeys() {
        return this.rowkeys;
    }

    public void setRowkeys(DimensionValueSet rowkeys) {
        this.rowkeys = rowkeys;
    }

    public boolean isModified() {
        for (UpdateDataColumn column : this.updateColumns.values()) {
            if (!column.isModified()) continue;
            return true;
        }
        return false;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("rowkey:");
        str.append(this.rowkeys).append("\n");
        str.append("updateColumns:");
        str.append(this.updateColumns.values()).append("\n");
        return str.toString();
    }
}

