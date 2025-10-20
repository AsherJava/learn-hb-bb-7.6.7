/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.parameter.extend.ds.hier;

import com.jiuqi.bi.parameter.model.datasource.DataSourceModel;
import com.jiuqi.bi.util.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class DSHierarchyDataSourceModel
extends DataSourceModel
implements Cloneable {
    public static final String TYPE = "com.jiuqi.bi.datasource.dshier";
    private String dsName;
    private String dsType;
    private String dsHierarchyName;
    private String dsPath;
    private static final String TAG_DSNAME = "dsName";
    private static final String TAG_DSTYPE = "dsType";
    private static final String TAG_DSHIER = "dsHier";
    private static final String TAG_DSPATH = "dsPath";

    public DSHierarchyDataSourceModel() {
        this.setType(TYPE);
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

    public String getDsPath() {
        return this.dsPath;
    }

    public void setDsPath(String dsPath) {
        this.dsPath = dsPath;
    }

    public String getDsType() {
        return this.dsType;
    }

    public void setDsType(String dsType) {
        this.dsType = dsType;
    }

    @Override
    protected void loadExt(JSONObject value) throws JSONException {
        this.dsName = value.getString(TAG_DSNAME);
        if (!value.isNull(TAG_DSTYPE)) {
            this.dsType = value.getString(TAG_DSTYPE);
        }
        this.dsHierarchyName = value.getString(TAG_DSHIER);
        if (!value.isNull(TAG_DSPATH)) {
            this.dsPath = value.getString(TAG_DSPATH);
        }
    }

    @Override
    protected void saveExt(JSONObject value) throws JSONException {
        value.put(TAG_DSNAME, (Object)this.dsName);
        if (StringUtils.isNotEmpty((String)this.dsType)) {
            value.put(TAG_DSTYPE, (Object)this.dsType);
        }
        value.put(TAG_DSHIER, (Object)this.dsHierarchyName);
        if (this.dsPath != null) {
            value.put(TAG_DSPATH, (Object)this.dsPath);
        }
    }

    @Override
    public DSHierarchyDataSourceModel clone() {
        try {
            return (DSHierarchyDataSourceModel)super.clone();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

