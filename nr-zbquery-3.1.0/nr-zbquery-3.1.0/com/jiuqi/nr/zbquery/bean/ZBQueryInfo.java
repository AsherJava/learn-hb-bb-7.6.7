/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.nr.zbquery.bean;

import org.json.JSONObject;

public class ZBQueryInfo {
    private String id;
    private String title;
    private String description;
    private long modifyTime;
    private String level;

    public ZBQueryInfo() {
    }

    public ZBQueryInfo(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String toJson() {
        JSONObject json = new JSONObject();
        json.put("id", (Object)this.id);
        json.put("title", (Object)this.title);
        json.put("description", (Object)this.description);
        json.put("modifyTime", this.modifyTime);
        return json.toString();
    }

    public void fromJson(JSONObject json) {
        if (json != null) {
            this.id = json.optString("id");
            this.title = json.optString("title");
            this.description = json.optString("description");
            this.modifyTime = json.optLong("modifyTime");
            this.level = json.optString("level");
        }
    }
}

