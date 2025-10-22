/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.estimation.service.dataio.model;

import com.jiuqi.nr.data.estimation.service.dataio.ITableCellValue;

public class TableCellValue
implements ITableCellValue {
    private Object oldValue;
    private Object newValue;
    private static final TableCellValue instance = new TableCellValue();

    private TableCellValue() {
    }

    @Override
    public Object getOldValue() {
        return this.oldValue;
    }

    public void setOldValue(Object oldValue) {
        this.oldValue = oldValue;
    }

    @Override
    public Object getNewValue() {
        return this.newValue;
    }

    public void setNewValue(Object newValue) {
        this.newValue = newValue;
    }

    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public static TableCellValue getInstance(Object newValue, Object oldValue) {
        TableCellValue cell = TableCellValue.getInstance(newValue);
        cell.setOldValue(oldValue);
        return cell;
    }

    public static TableCellValue getInstance(Object newValue) {
        TableCellValue cell = TableCellValue.getInstance();
        assert (cell != null);
        cell.setNewValue(newValue);
        return cell;
    }

    public static TableCellValue getInstance() {
        try {
            return (TableCellValue)instance.clone();
        }
        catch (CloneNotSupportedException e) {
            return null;
        }
    }
}

