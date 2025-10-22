/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.setting;

import com.jiuqi.np.dataengine.setting.JoinType;
import com.jiuqi.np.dataengine.setting.SqlJoinOneItem;
import java.util.ArrayList;
import java.util.List;

public class SqlJoinItem {
    private JoinType joinType;
    private String srcTable;
    private String desTable;
    private List<SqlJoinOneItem> joinFieldsItems;

    public SqlJoinItem(String srcTable, String desTable) {
        this.srcTable = srcTable;
        this.desTable = desTable;
    }

    public String getSrcTable() {
        return this.srcTable;
    }

    public void setSrcTable(String value) {
        this.srcTable = value;
    }

    public String getDesTable() {
        return this.desTable;
    }

    public void setDesTable(String value) {
        this.desTable = value;
    }

    public JoinType getJoinType() {
        return this.joinType;
    }

    public void setJoinType(JoinType value) {
        this.joinType = value;
    }

    public List<SqlJoinOneItem> getJoinFields() {
        return this.joinFieldsItems;
    }

    public void addJoinItem(SqlJoinOneItem joinField) {
        if (this.joinFieldsItems == null) {
            this.joinFieldsItems = new ArrayList<SqlJoinOneItem>();
        }
        this.joinFieldsItems.add(joinField);
    }
}

