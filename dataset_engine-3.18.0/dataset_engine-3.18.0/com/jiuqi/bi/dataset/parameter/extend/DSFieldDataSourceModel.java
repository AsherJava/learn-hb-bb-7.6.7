/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.dataset.parameter.extend;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import org.json.JSONException;
import org.json.JSONObject;

public class DSFieldDataSourceModel
extends AbstractParameterDataSourceModel {
    public static final String TYPE = "com.jiuqi.bi.datasource.dsfield";
    private String dsName;
    private String dsType;
    private String dsFieldName;
    private static final String TAG_DSNAME = "dsName";
    private static final String TAG_DSTYPE = "dsType";
    private static final String TAG_DSFIELD = "dsField";

    public String getType() {
        return TYPE;
    }

    public String getDsName() {
        return this.dsName;
    }

    public void setDsName(String dsName) {
        this.dsName = dsName;
    }

    public String getDsFieldName() {
        return this.dsFieldName;
    }

    public void setDsFieldName(String dsFieldName) {
        this.dsFieldName = dsFieldName;
    }

    public String getDsType() {
        return this.dsType;
    }

    public void setDsType(String dsType) {
        this.dsType = dsType;
    }

    protected void fromExtJson(JSONObject json) throws JSONException {
        this.dsName = json.getString(TAG_DSNAME);
        if (!json.isNull(TAG_DSTYPE)) {
            this.dsType = json.getString(TAG_DSTYPE);
        }
        this.dsFieldName = json.getString(TAG_DSFIELD);
    }

    protected void toExtJson(JSONObject value) throws JSONException {
        value.put(TAG_DSNAME, (Object)this.dsName);
        if (StringUtils.isNotEmpty((String)this.dsType)) {
            value.put(TAG_DSTYPE, (Object)this.dsType);
        }
        value.put(TAG_DSFIELD, (Object)this.dsFieldName);
    }

    public DSFieldDataSourceModel clone() {
        try {
            return (DSFieldDataSourceModel)super.clone();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

