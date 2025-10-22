/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.engine.summary.parse;

import com.jiuqi.nr.data.engine.summary.parse.SumNode;
import java.util.ArrayList;
import java.util.List;

public class SumTable {
    private String tableName;
    private List<SumNode> nodes = new ArrayList<SumNode>();
    private boolean isMain;
    private String tableOrder;

    public SumTable(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public boolean isMain() {
        return this.isMain;
    }

    public void setMain(boolean isMain) {
        this.isMain = isMain;
    }

    public List<SumNode> getNodes() {
        return this.nodes;
    }

    public String getTableOrder() {
        return this.tableOrder;
    }

    public void setTableOrder(String tableOrder) {
        this.tableOrder = tableOrder;
    }
}

