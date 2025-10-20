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

public class DSHierarchyDataSourceModel
extends AbstractParameterDataSourceModel {
    public static final String TYPE = "com.jiuqi.bi.datasource.dshier";
    private String dsName;
    private String dsType;
    private String dsHierarchyName;
    private static final String TAG_DSNAME = "dsName";
    private static final String TAG_DSTYPE = "dsType";
    private static final String TAG_DSHIER = "dsHier";

    public String getType() {
        return TYPE;
    }

    public String getDsName() {
        return this.dsName;
    }

    public void setDsName(String dsName) {
        this.dsName = dsName;
    }

    public String getDsHierarchyName() {
        return this.dsHierarchyName;
    }

    public void setDsHierarchyName(String dsHierarchyName) {
        this.dsHierarchyName = dsHierarchyName;
    }

    public String getDsType() {
        return this.dsType;
    }

    public void setDsType(String dsType) {
        this.dsType = dsType;
    }

    protected void fromExtJson(JSONObject value) throws JSONException {
        this.dsName = value.getString(TAG_DSNAME);
        if (!value.isNull(TAG_DSTYPE)) {
            this.dsType = value.getString(TAG_DSTYPE);
        }
        this.dsHierarchyName = value.getString(TAG_DSHIER);
    }

    protected void toExtJson(JSONObject value) throws JSONException {
        value.put(TAG_DSNAME, (Object)this.dsName);
        if (StringUtils.isNotEmpty((String)this.dsType)) {
            value.put(TAG_DSTYPE, (Object)this.dsType);
        }
        value.put(TAG_DSHIER, (Object)this.dsHierarchyName);
    }

    public DSHierarchyDataSourceModel clone() {
        try {
            return (DSHierarchyDataSourceModel)super.clone();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

