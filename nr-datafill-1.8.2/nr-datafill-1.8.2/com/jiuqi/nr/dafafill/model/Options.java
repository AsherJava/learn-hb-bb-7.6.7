/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dafafill.model;

import com.jiuqi.nr.dafafill.model.enums.HeaderMode;
import java.io.Serializable;

public class Options
implements Serializable {
    private static final long serialVersionUID = 1L;
    private int pageSize = 50;
    private HeaderMode tableHeaderMode = HeaderMode.MERGE;
    private boolean lockTableHead = false;
    private boolean displayIndent;
    private boolean displaySequence;
    private boolean displayZBCode;

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public HeaderMode getTableHeaderMode() {
        return this.tableHeaderMode;
    }

    public void setTableHeaderMode(HeaderMode tableHeaderMode) {
        this.tableHeaderMode = tableHeaderMode;
    }

    public boolean isLockTableHead() {
        return this.lockTableHead;
    }

    public void setLockTableHead(boolean lockTableHead) {
        this.lockTableHead = lockTableHead;
    }

    public boolean isDisplayIndent() {
        return this.displayIndent;
    }

    public void setDisplayIndent(boolean displayIndent) {
        this.displayIndent = displayIndent;
    }

    public boolean isDisplaySequence() {
        return this.displaySequence;
    }

    public void setDisplaySequence(boolean displaySequence) {
        this.displaySequence = displaySequence;
    }

    public boolean isDisplayZBCode() {
        return this.displayZBCode;
    }

    public void setDisplayZBCode(boolean displayZBCode) {
        this.displayZBCode = displayZBCode;
    }
}

