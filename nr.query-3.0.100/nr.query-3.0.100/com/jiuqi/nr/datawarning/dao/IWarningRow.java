/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  org.json.JSONObject
 */
package com.jiuqi.nr.datawarning.dao;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import org.json.JSONObject;

public interface IWarningRow {
    public int getColIndex();

    public DimensionValueSet getRowDimensionValueSet();

    public String getWarnId();

    public AbstractData getDataValueList();

    public JSONObject getResultList();

    public void setResultList(JSONObject var1);

    public String getOrder();

    public String getFieldCode();

    public void setFieldCode(String var1);

    public void setOrder(String var1);

    public void setWarnId(String var1);

    public String getFieldSettingCode();

    public void setFieldSettingCode(String var1);
}

