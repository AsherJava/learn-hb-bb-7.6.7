/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.queryparam;

public class SortItem {
    private String sortCode;
    private boolean sortDesc;

    public SortItem(String sortCode, boolean sortDesc) {
        this.sortCode = sortCode;
        this.sortDesc = sortDesc;
    }

    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }

    public String getSortCode() {
        return this.sortCode;
    }

    public void setSortCode(boolean sortDesc) {
        this.sortDesc = sortDesc;
    }

    public boolean getSortDesc() {
        return this.sortDesc;
    }
}

