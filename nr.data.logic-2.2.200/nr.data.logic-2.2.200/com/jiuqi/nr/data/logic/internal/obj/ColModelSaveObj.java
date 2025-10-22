/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.data.logic.internal.obj;

import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;

public class ColModelSaveObj {
    private ColumnModelDefine column;
    private int index;

    public ColModelSaveObj(ColumnModelDefine columnModel, int i) {
        this.column = columnModel;
        this.index = i;
    }

    public ColumnModelDefine getColumn() {
        return this.column;
    }

    public void setColumn(ColumnModelDefine column) {
        this.column = column;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}

