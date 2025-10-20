/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.quickreport.model;

import java.io.Serializable;
import java.util.Base64;
import org.json.JSONException;
import org.json.JSONObject;

public final class PrintTemplete
implements Serializable,
Cloneable {
    private static final long serialVersionUID = -1503974705735889020L;
    private String name;
    private byte[] data;
    private String description;
    private static final String PT_NAME = "name";
    private static final String PT_DATA = "data";
    private static final String PT_DESC = "description";

    public PrintTemplete() {
        this(null);
    }

    public PrintTemplete(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return this.data;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(PT_NAME, (Object)this.name);
        String dataStr = this.data == null ? null : Base64.getEncoder().encodeToString(this.data);
        json.put(PT_DATA, (Object)dataStr);
        json.put(PT_DESC, (Object)this.description);
        return json;
    }

    public void fromJSON(JSONObject json) throws JSONException {
        this.name = json.optString(PT_NAME, "");
        String dataStr = json.optString(PT_DATA, null);
        this.data = dataStr == null ? null : Base64.getDecoder().decode(dataStr);
        this.description = json.optString(PT_DESC, null);
    }
}

