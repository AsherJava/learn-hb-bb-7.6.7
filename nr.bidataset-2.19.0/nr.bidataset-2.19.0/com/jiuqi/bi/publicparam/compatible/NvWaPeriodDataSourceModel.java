/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.bap.parameter.extend.compatible.remote.RemoteDataSourceModel
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.publicparam.compatible;

import com.jiuqi.nvwa.bap.parameter.extend.compatible.remote.RemoteDataSourceModel;
import org.json.JSONException;
import org.json.JSONObject;

public class NvWaPeriodDataSourceModel
extends RemoteDataSourceModel {
    private static final String TAG_PERIOD_TYPE = "periodType";
    private static final String TAG_PERIOD_START_END = "periodStartEnd";
    private static final String TAG_PARANAME = "paraName";
    private int periodType = -1;
    private String periodStartEnd;
    private String paraName;

    public NvWaPeriodDataSourceModel() {
        super("com.jiuqi.publicparam.datasource.date");
        this.setType("com.jiuqi.publicparam.datasource.date");
    }

    public NvWaPeriodDataSourceModel(String serviceName) {
        super(serviceName);
    }

    public int getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(int periodType) {
        this.periodType = periodType;
    }

    public String getPeriodStartEnd() {
        return this.periodStartEnd;
    }

    public void setPeriodStartEnd(String periodStartEnd) {
        this.periodStartEnd = periodStartEnd;
    }

    public String getParaName() {
        return this.paraName;
    }

    public void setParaName(String paraName) {
        this.paraName = paraName;
    }

    protected void saveExt(JSONObject value) throws JSONException {
        super.saveExt(value);
        value.put(TAG_PERIOD_TYPE, this.periodType);
        value.put(TAG_PERIOD_START_END, (Object)this.periodStartEnd);
        value.put(TAG_PARANAME, (Object)this.paraName);
    }

    protected void loadExt(JSONObject value) throws JSONException {
        super.loadExt(value);
        if (value.has(TAG_PERIOD_TYPE)) {
            this.periodType = value.getInt(TAG_PERIOD_TYPE);
        }
        if (value.has(TAG_PERIOD_START_END)) {
            this.periodStartEnd = value.getString(TAG_PERIOD_START_END);
        }
        if (value.has(TAG_PARANAME)) {
            this.paraName = value.getString(TAG_PARANAME);
        }
    }

    public NvWaPeriodDataSourceModel clone() {
        try {
            return (NvWaPeriodDataSourceModel)super.clone();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

