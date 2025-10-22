/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  org.json.JSONObject
 */
package com.jiuqi.nr.datawarning.dao.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.nr.datawarning.dao.IWarningRow;
import com.jiuqi.nr.datawarning.defines.DataWarningDefine;
import org.json.JSONObject;

public class WarningRowDefine
implements IWarningRow {
    protected int colIndex;
    protected DimensionValueSet rowDimensionValueSet;
    protected String warnId;
    protected String fieldKey;
    protected String Order;
    protected String fieldSettingCode;
    protected AbstractData dataValue;
    protected JSONObject resultList;

    public WarningRowDefine(int colIndex, DimensionValueSet rowDimensionValueSet, DataWarningDefine warDefine, String fieldCode) {
        this.colIndex = colIndex;
        this.rowDimensionValueSet = rowDimensionValueSet;
        this.warnId = warDefine.getId();
        this.fieldKey = fieldCode;
        this.Order = warDefine.getOrder();
        this.fieldSettingCode = warDefine.getFieldSettingCode();
        this.resultList = new JSONObject();
    }

    @Override
    public int getColIndex() {
        return this.colIndex;
    }

    @Override
    public DimensionValueSet getRowDimensionValueSet() {
        return this.rowDimensionValueSet;
    }

    @Override
    public AbstractData getDataValueList() {
        return this.dataValue;
    }

    @Override
    public JSONObject getResultList() {
        return this.resultList;
    }

    @Override
    public void setResultList(JSONObject list) {
        this.resultList = list;
    }

    @Override
    public String getWarnId() {
        return this.warnId;
    }

    @Override
    public void setWarnId(String warnId) {
        this.warnId = warnId;
    }

    @Override
    public String getOrder() {
        return this.Order;
    }

    @Override
    public void setOrder(String Order) {
        this.Order = Order;
    }

    @Override
    public String getFieldSettingCode() {
        return this.fieldSettingCode;
    }

    @Override
    public void setFieldSettingCode(String fieldSettingCode) {
        this.fieldSettingCode = fieldSettingCode;
    }

    @Override
    public String getFieldCode() {
        return this.fieldKey;
    }

    @Override
    public void setFieldCode(String fieldCode) {
        this.fieldKey = fieldCode;
    }
}

