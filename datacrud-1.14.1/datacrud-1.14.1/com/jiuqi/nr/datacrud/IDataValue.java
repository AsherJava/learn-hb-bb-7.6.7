/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.nr.data.access.param.IAccessResult
 */
package com.jiuqi.nr.datacrud;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.IRowData;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

public interface IDataValue {
    public IMetaData getMetaData();

    public boolean getAsNull();

    public boolean getAsBool() throws DataTypeException;

    public int getAsInt() throws DataTypeException;

    public double getAsFloat() throws DataTypeException;

    public BigDecimal getAsCurrency() throws DataTypeException;

    public Date getAsDate() throws DataTypeException;

    public Time getAsDateTime() throws DataTypeException;

    public String getAsString();

    public Object getAsObject() throws DataTypeException;

    public IAccessResult canRead();

    public AbstractData getAbstractData();

    public void setAbstractData(AbstractData var1);

    public IRowData getRowData();
}

