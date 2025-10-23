/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.model.cell;

import com.jiuqi.nr.summary.model.cell.ZbProperty;
import java.util.Objects;

public class SummaryZb
extends ZbProperty {
    private String fieldKey;
    private String name;
    private String title;
    private String tableName;

    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        SummaryZb summaryZb = (SummaryZb)o;
        return this.fieldKey.equals(summaryZb.fieldKey) && this.name.equals(summaryZb.name) && this.title.equals(summaryZb.title) && this.tableName.equals(summaryZb.tableName);
    }

    public int hashCode() {
        return Objects.hash(this.fieldKey, this.name, this.title, this.tableName);
    }
}

