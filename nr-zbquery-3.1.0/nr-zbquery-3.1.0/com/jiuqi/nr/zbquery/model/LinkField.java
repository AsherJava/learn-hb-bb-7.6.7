/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.nr.zbquery.model;

import com.jiuqi.nr.zbquery.model.LinkResourceNode;
import com.jiuqi.nr.zbquery.model.OpenType;
import org.json.JSONException;
import org.json.JSONObject;

public class LinkField {
    private String fullName;
    private LinkResourceNode resourceNode;
    private OpenType openType = OpenType.FUNCTAB;
    private int width;
    private int height;

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LinkResourceNode getResourceNode() {
        return this.resourceNode;
    }

    public void setResourceNode(LinkResourceNode resourceNode) {
        this.resourceNode = resourceNode;
    }

    public OpenType getOpenType() {
        return this.openType;
    }

    public void setOpenType(OpenType openType) {
        this.openType = openType;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("fullName", (Object)this.fullName);
        json.put("resourceNode", (Object)this.resourceNode.toJSON());
        json.put("openType", (Object)this.openType.value());
        json.put("width", this.width);
        json.put("height", this.height);
        return json;
    }

    public void fromJSON(JSONObject json) throws JSONException {
        this.fullName = json.optString("fullName");
        this.resourceNode = new LinkResourceNode();
        this.resourceNode.fromJSON(json.getJSONObject("resourceNode"));
        this.openType = OpenType.valueOf(json.optString("openType", OpenType.FUNCTAB.value()));
        this.width = json.optInt("width");
        this.height = json.optInt("height");
    }
}

