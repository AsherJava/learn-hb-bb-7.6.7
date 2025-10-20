/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.parameter.storage;

import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.util.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class ParameterBean {
    private String id;
    private String title;
    private String folder;
    private int dataType = DataType.STRING.value();
    private boolean isZBParameter;
    private static final String TAG_ID = "id";
    private static final String TAG_TITLE = "title";
    private static final String TAG_PARENTID = "folder";
    private static final String TAG_DATATYPE = "dataType";
    private static final String TAG_ISZB = "isZB";

    public void save(JSONObject value) throws JSONException {
        value.put(TAG_ID, (Object)this.id);
        value.put(TAG_TITLE, (Object)this.title);
        if (StringUtils.isNotEmpty((String)this.folder)) {
            value.put(TAG_PARENTID, (Object)this.folder);
        }
        value.put(TAG_DATATYPE, this.dataType);
        value.put(TAG_ISZB, this.isZBParameter);
    }

    public void load(JSONObject value) throws JSONException {
        this.id = value.getString(TAG_ID);
        this.title = value.getString(TAG_TITLE);
        if (!value.isNull(TAG_PARENTID)) {
            this.folder = value.getString(TAG_PARENTID);
        }
        this.dataType = value.getInt(TAG_DATATYPE);
        this.isZBParameter = value.getBoolean(TAG_ISZB);
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public int getDataType() {
        return this.dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public boolean isZBParameter() {
        return this.isZBParameter;
    }

    public void setZBParameter(boolean isZBParameter) {
        this.isZBParameter = isZBParameter;
    }

    public String getFolder() {
        return this.folder;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }
}

