/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.impl.data;

import com.jiuqi.va.biz.intf.data.DataField;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataTable;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.biz.ruler.intf.TriggerEvent;
import java.util.Objects;

public class DataEventImpl
implements TriggerEvent {
    private String triggerType;
    private DataTable table;
    private DataRow row;
    private DataField field;

    public DataEventImpl(String triggerType, DataTable table, DataRow row, DataField field) {
        this.triggerType = triggerType;
        this.table = table;
        this.row = row;
        this.field = field;
    }

    @Override
    public String getTriggerType() {
        return this.triggerType;
    }

    void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
    }

    @Override
    public DataTable getTable() {
        return this.table;
    }

    void setTable(DataTable table) {
        this.table = table;
    }

    @Override
    public DataRow getRow() {
        return this.row;
    }

    void setRow(DataRow row) {
        this.row = row;
    }

    @Override
    public DataField getField() {
        return this.field;
    }

    void setField(DataField field) {
        this.field = field;
    }

    public int hashCode() {
        return Objects.hash(this.triggerType, this.table, this.row, this.field);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DataEventImpl)) {
            return false;
        }
        DataEventImpl o = (DataEventImpl)obj;
        return o.triggerType == this.triggerType && o.table == this.table && o.row == this.row && o.field == this.field;
    }

    public String toString() {
        return String.format("%s,%s,%s,%s", this.triggerType, this.table == null ? null : this.table.getName(), this.field == null ? "" : this.field.getName(), this.row == null ? "" : Convert.cast(this.row.getId(), String.class));
    }
}

