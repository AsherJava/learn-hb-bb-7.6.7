/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.bi.core.jobs.extension.item;

import org.json.JSONObject;

public class FolderItem {
    private String guid;
    private String title;
    private String parentGuid;
    private String type;
    private String order;

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getGuid() {
        return this.guid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public String getParentGuid() {
        return this.parentGuid;
    }

    public void setParentGuid(String parentGuid) {
        this.parentGuid = parentGuid;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("guid", (Object)this.guid);
        json.put("title", (Object)this.title);
        json.put("parentGuid", (Object)this.parentGuid);
        json.put("type", (Object)this.type);
        json.put("order", (Object)this.order);
        return json;
    }
}

