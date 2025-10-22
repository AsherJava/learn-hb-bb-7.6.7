/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.common;

import com.jiuqi.np.dataengine.common.QueryField;

public class LookupItem {
    private QueryField keyField;
    private QueryField valueField;

    public LookupItem(QueryField keyField, QueryField valueField) {
        this.setKeyField(keyField);
        this.setValueField(valueField);
    }

    public QueryField getKeyField() {
        return this.keyField;
    }

    public void setKeyField(QueryField keyField) {
        this.keyField = keyField;
    }

    public QueryField getValueField() {
        return this.valueField;
    }

    public void setValueField(QueryField valueField) {
        this.valueField = valueField;
    }
}

