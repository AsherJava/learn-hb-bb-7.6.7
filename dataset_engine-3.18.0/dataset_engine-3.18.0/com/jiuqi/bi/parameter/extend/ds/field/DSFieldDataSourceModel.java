/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.parameter.extend.ds.field;

import com.jiuqi.bi.parameter.model.datasource.DataSourceModel;
import com.jiuqi.bi.util.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class DSFieldDataSourceModel
extends DataSourceModel
implements Cloneable {
    public static final String TYPE = "com.jiuqi.bi.datasource.dsfield";
    private String dsName;
    private String dsType;
    private String dsFieldName;
    private String dsPath;
    private static final String TAG_DSNAME = "dsName";
    private static final String TAG_DSTYPE = "dsType";
    private static final String TAG_DSFIELD = "dsField";
    private static final String TAG_DSPATH = "dsPath";

    public DSFieldDataSourceModel() {
        this.setType(TYPE);
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

    public String getDsPath() {
        return this.dsPath;
    }

    public String getDsType() {
        return this.dsType;
    }

    public void setDsType(String dsType) {
        this.dsType = dsType;
    }

    public void setDsPath(String dsPath) {
        this.dsPath = dsPath;
    }

    @Override
    protected void loadExt(JSONObject value) throws JSONException {
        this.dsName = value.getString(TAG_DSNAME);
        if (!value.isNull(TAG_DSTYPE)) {
            this.dsType = value.getString(TAG_DSTYPE);
        }
        this.dsFieldName = value.getString(TAG_DSFIELD);
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
        value.put(TAG_DSFIELD, (Object)this.dsFieldName);
        if (this.dsPath != null) {
            value.put(TAG_DSPATH, (Object)this.dsPath);
        }
    }

    @Override
    public DSFieldDataSourceModel clone() {
        try {
            return (DSFieldDataSourceModel)super.clone();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

