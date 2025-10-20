/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.nvwa.framework.parameter.storage;

import com.jiuqi.bi.util.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class ParameterResourceTreeNode {
    private String id;
    private String title;
    private String parentId;
    private String modifyTime;
    private static final String TAG_ID = "id";
    private static final String TAG_TITLE = "title";
    private static final String TAG_PARENTID = "parentId";
    private static final String TAG_MODIFY = "modifyTime";

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

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyTime() {
        return this.modifyTime;
    }

    public void save(JSONObject value) throws JSONException {
        value.put(TAG_ID, (Object)this.id);
        value.put(TAG_TITLE, (Object)this.title);
        if (StringUtils.isNotEmpty((String)this.parentId)) {
            value.put(TAG_PARENTID, (Object)this.parentId);
        }
        if (StringUtils.isNotEmpty((String)this.modifyTime)) {
            value.put(TAG_MODIFY, (Object)this.modifyTime);
        }
    }

    public void load(JSONObject value) throws JSONException {
        this.id = value.getString(TAG_ID);
        this.title = value.getString(TAG_TITLE);
        if (!value.isNull(TAG_PARENTID)) {
            this.parentId = value.getString(TAG_PARENTID);
        }
        if (!value.isNull(TAG_MODIFY)) {
            this.modifyTime = value.getString(TAG_MODIFY);
        }
    }
}

