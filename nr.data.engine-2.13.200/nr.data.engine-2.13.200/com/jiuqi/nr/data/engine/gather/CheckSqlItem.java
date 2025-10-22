/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.engine.gather;

import com.jiuqi.nr.data.engine.gather.FieldItem;
import com.jiuqi.nr.data.engine.gather.SqlItem;
import java.util.List;

public class CheckSqlItem {
    private List<SqlItem> sqlItems;
    private List<FieldItem> fieldItems;

    public List<SqlItem> getSqlItems() {
        return this.sqlItems;
    }

    public void setSqlItems(List<SqlItem> sqlItems) {
        this.sqlItems = sqlItems;
    }

    public List<FieldItem> getFieldItems() {
        return this.fieldItems;
    }

    public void setFieldItems(List<FieldItem> fieldItems) {
        this.fieldItems = fieldItems;
    }
}

