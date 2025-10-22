/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.data.AbstractData
 */
package com.jiuqi.nr.datacrud.api;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datacrud.Measure;
import com.jiuqi.nr.datacrud.impl.measure.MeasureData;

public interface IDataValueBalanceActuator {
    public void setMeasure(Measure var1);

    public void setMeasure(MeasureData var1);

    public void setNumDecimalPlaces(Integer var1);

    public void balanceValue(IRowData var1);

    public void balanceValue(IDataValue var1);

    public AbstractData balanceValue(IRowData var1, IMetaData var2, AbstractData var3);
}

