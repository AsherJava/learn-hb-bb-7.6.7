/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.nr.data.access.param.IAccessResult
 */
package com.jiuqi.nr.datacrud.impl;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.impl.RowData;
import com.jiuqi.nr.datacrud.impl.out.CrudException;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

public class DataValue
implements IDataValue {
    protected IMetaData metaData;
    protected RowData rowData;
    protected AbstractData data;

    protected DataValue() {
    }

    public DataValue(IMetaData metaData) {
        this.metaData = metaData;
    }

    public DataValue(IMetaData metaData, AbstractData data) {
        this.metaData = metaData;
        this.data = data;
    }

    @Override
    public IMetaData getMetaData() {
        return this.metaData;
    }

    public void setMetaData(IMetaData metaData) {
        this.metaData = metaData;
    }

    @Override
    public boolean getAsNull() {
        if (this.access()) {
            return this.data.getAsNull();
        }
        throw new CrudException(4002);
    }

    @Override
    public boolean getAsBool() throws DataTypeException {
        if (this.access()) {
            return this.data.getAsBool();
        }
        throw new CrudException(4002);
    }

    @Override
    public int getAsInt() throws DataTypeException {
        if (this.access()) {
            return this.data.getAsInt();
        }
        throw new CrudException(4002);
    }

    @Override
    public double getAsFloat() throws DataTypeException {
        if (this.access()) {
            return this.data.getAsFloat();
        }
        throw new CrudException(4002);
    }

    @Override
    public BigDecimal getAsCurrency() throws DataTypeException {
        if (this.access()) {
            return this.data.getAsCurrency();
        }
        throw new CrudException(4002);
    }

    @Override
    public Date getAsDate() throws DataTypeException {
        if (this.access()) {
            return this.data.getAsDateObj();
        }
        throw new CrudException(4002);
    }

    @Override
    public Time getAsDateTime() throws DataTypeException {
        if (this.access()) {
            return this.data.getAsDateTimeObj();
        }
        throw new CrudException(4002);
    }

    @Override
    public String getAsString() {
        if (this.access()) {
            return this.data.getAsString();
        }
        throw new CrudException(4002);
    }

    @Override
    public Object getAsObject() throws DataTypeException {
        if (this.access()) {
            return this.data.getAsObject();
        }
        throw new CrudException(4002);
    }

    @Override
    public IAccessResult canRead() {
        return this.metaData.canRead();
    }

    public boolean access() {
        return true;
    }

    @Override
    public AbstractData getAbstractData() {
        return this.data;
    }

    @Override
    public void setAbstractData(AbstractData data) {
        this.data = data;
    }

    @Override
    public RowData getRowData() {
        return this.rowData;
    }

    public void setRowData(RowData rowData) {
        this.rowData = rowData;
    }

    public String toString() {
        if (this.data != null) {
            return this.data.getAsString();
        }
        return null;
    }
}

