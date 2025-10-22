/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.entity.engine.intf;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.setting.IFieldsInfo;

public interface IDataRow {
    public IFieldsInfo getFieldsInfo();

    public DimensionValueSet getRowKeys();

    public AbstractData getValue(String var1) throws RuntimeException;

    public String getAsString(String var1) throws RuntimeException;

    public AbstractData getValue(int var1) throws RuntimeException;

    public String getAsString(int var1) throws RuntimeException;

    public Object getAsObject(int var1) throws RuntimeException;
}

