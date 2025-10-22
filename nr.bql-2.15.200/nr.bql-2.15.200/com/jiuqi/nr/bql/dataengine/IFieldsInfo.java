/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.bql.dataengine;

import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;

public interface IFieldsInfo {
    public int getFieldCount();

    public int getDataType(int var1);

    public ColumnModelDefine getFieldDefine(int var1);

    public int indexOf(String var1);

    public int indexOf(ColumnModelDefine var1);

    public void setupField(int var1, ColumnModelDefine var2);

    public void setupField(int var1, int var2);

    public void reset();

    public void appendFields(int var1);
}

