/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.parameter.storage;

import com.jiuqi.bi.util.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class ParameterFolderBean {
    private String id;
    private String title;
    private String parentId;
    private String storageType;
    private static final String TAG_ID = "id";
    private static final String TAG_TITLE = "title";
    private static final String TAG_PARENTID = "parentId";
    private static final String TAG_STORAGETYPE = "storageType";

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void save(JSONObject value) throws JSONException {
        value.put(TAG_ID, (Object)this.id);
        value.put(TAG_TITLE, (Object)this.title);
        if (StringUtils.isNotEmpty((String)this.parentId)) {
            value.put(TAG_PARENTID, (Object)this.parentId);
        }
        if (StringUtils.isNotEmpty((String)this.storageType)) {
            value.put(TAG_STORAGETYPE, (Object)this.storageType);
        }
    }

    public void load(JSONObject value) throws JSONException {
        this.id = value.getString(TAG_ID);
        this.title = value.getString(TAG_TITLE);
        if (!value.isNull(TAG_PARENTID)) {
            this.parentId = value.getString(TAG_PARENTID);
        }
        if (!value.isNull(TAG_STORAGETYPE)) {
            this.storageType = value.getString(TAG_STORAGETYPE);
        }
    }

    public String getStorageType() {
        return this.storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }
}

