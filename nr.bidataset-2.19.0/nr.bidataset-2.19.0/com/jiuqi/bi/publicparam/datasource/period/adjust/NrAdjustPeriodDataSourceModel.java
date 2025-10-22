/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.publicparam.datasource.period.adjust;

import com.jiuqi.bi.publicparam.datasource.period.NrPeriodDataSourceModel;
import org.json.JSONException;
import org.json.JSONObject;

public class NrAdjustPeriodDataSourceModel
extends NrPeriodDataSourceModel {
    public static final String TYPE = "com.jiuqi.publicparam.datasource.adjustDate";
    public static final String TITLE = "\u62a5\u8868\u65f6\u671f&\u8c03\u6574\u671f";
    private static final String TAG_DATA_SCHEME = "dataScheme";
    protected String dataScheme;

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    protected void fromExtJson(JSONObject json) throws JSONException {
        super.fromExtJson(json);
        if (json.has(TAG_DATA_SCHEME)) {
            this.dataScheme = json.optString(TAG_DATA_SCHEME);
        }
    }

    @Override
    protected void toExtJson(JSONObject json) throws JSONException {
        super.toExtJson(json);
        json.put(TAG_DATA_SCHEME, (Object)this.dataScheme);
    }
}

