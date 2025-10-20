/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.tree.ObjectVistor
 */
package com.jiuqi.bi.dataset.parameter.extend;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.util.tree.ObjectVistor;

public class BIDataRowObjectVistor
implements ObjectVistor {
    private int codeIndex;
    private int parentIndex;

    public BIDataRowObjectVistor() {
    }

    public BIDataRowObjectVistor(int codeIndex, int parentIndex) {
        this.codeIndex = codeIndex;
        this.parentIndex = parentIndex;
    }

    public String getCode(Object o) {
        BIDataRow row = (BIDataRow)o;
        if (this.codeIndex != 0 && this.codeIndex != -1) {
            return row.getString(this.codeIndex);
        }
        return row.getString(0);
    }

    public String getParentCode(Object o) {
        BIDataRow row = (BIDataRow)o;
        if (this.parentIndex != 0 && this.parentIndex != -1) {
            return row.getString(this.parentIndex);
        }
        if (row.getBuffer().length >= 3) {
            return row.getString(2);
        }
        if (row.getBuffer().length == 2) {
            return row.getString(1);
        }
        return "";
    }
}

