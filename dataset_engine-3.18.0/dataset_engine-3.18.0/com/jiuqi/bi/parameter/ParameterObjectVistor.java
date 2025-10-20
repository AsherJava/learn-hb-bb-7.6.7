/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.util.tree.ObjectVistor
 */
package com.jiuqi.bi.parameter;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.util.tree.ObjectVistor;

public class ParameterObjectVistor
implements ObjectVistor {
    private int codeIndex;
    private int parentIndex;

    public ParameterObjectVistor() {
    }

    public ParameterObjectVistor(int codeIndex, int parentIndex) {
        this.codeIndex = codeIndex;
        this.parentIndex = parentIndex;
    }

    public String getCode(Object o) {
        DataRow row = (DataRow)o;
        if (this.codeIndex != 0 && this.codeIndex != -1) {
            return row.getString(this.codeIndex);
        }
        return row.getString(0);
    }

    public String getParentCode(Object o) {
        DataRow row = (DataRow)o;
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

