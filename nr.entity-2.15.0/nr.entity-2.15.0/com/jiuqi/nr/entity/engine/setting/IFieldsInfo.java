/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 */
package com.jiuqi.nr.entity.engine.setting;

import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nvwa.definition.common.ColumnModelType;

public interface IFieldsInfo {
    public int getFieldCount();

    public IEntityAttribute getFieldByIndex(int var1);

    public ColumnModelType getDataType(String var1);

    public IEntityAttribute getFieldDefine(String var1);

    public void setupField(String var1, IEntityAttribute var2);

    public void setupField(String var1, ColumnModelType var2);

    public void reset();
}

