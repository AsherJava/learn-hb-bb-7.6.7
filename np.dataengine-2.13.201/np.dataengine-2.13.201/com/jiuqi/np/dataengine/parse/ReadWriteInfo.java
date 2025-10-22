/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.parse;

import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.common.QueryTable;

public class ReadWriteInfo {
    private QueryFields readQueryFields;
    private QueryFields writeQueryFields;
    private QueryTable writeTable;
    private boolean clearTable;

    public QueryFields getReadQueryFields() {
        return this.readQueryFields;
    }

    public QueryFields getWriteQueryFields() {
        return this.writeQueryFields;
    }

    public QueryTable getWriteTable() {
        return this.writeTable;
    }

    public boolean isClearTable() {
        return this.clearTable;
    }

    public void setReadQueryFields(QueryFields readQueryFields) {
        this.readQueryFields = readQueryFields;
    }

    public void setWriteQueryFields(QueryFields writeQueryFields) {
        this.writeQueryFields = writeQueryFields;
    }

    public void setWriteTable(QueryTable writeTable) {
        this.writeTable = writeTable;
    }

    public void setClearTable(boolean clearTable) {
        this.clearTable = clearTable;
    }
}

