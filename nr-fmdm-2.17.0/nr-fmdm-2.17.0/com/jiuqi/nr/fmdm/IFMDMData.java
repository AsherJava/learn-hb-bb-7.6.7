/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 */
package com.jiuqi.nr.fmdm;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.entity.engine.data.AbstractData;

public interface IFMDMData {
    public String getFMDMKey();

    public DimensionValueSet getMasterKey();

    public AbstractData getValue(String var1);

    public Object getAsObject(String var1);

    public AbstractData getEntityValue(String var1);

    public AbstractData getDataValue(String var1);

    public AbstractData getInfoValue(String var1);

    public Object getEntityAsObject(String var1);

    public Object getDataAsObject(String var1);

    public Object getInfoAsObject(String var1);
}

