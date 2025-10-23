/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.datascheme.web.param;

import com.jiuqi.nr.query.datascheme.web.param.BaseNodeVO;

public class TableModelLoadVO {
    private boolean subTable;
    private String mainTable;
    private BaseNodeVO parent;
    private boolean leaf;

    public boolean isSubTable() {
        return this.subTable;
    }

    public void setSubTable(boolean subTable) {
        this.subTable = subTable;
    }

    public BaseNodeVO getParent() {
        return this.parent;
    }

    public void setParent(BaseNodeVO parent) {
        this.parent = parent;
    }

    public boolean isLeaf() {
        return this.leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public String getMainTable() {
        return this.mainTable;
    }

    public void setMainTable(String mainTable) {
        this.mainTable = mainTable;
    }
}

