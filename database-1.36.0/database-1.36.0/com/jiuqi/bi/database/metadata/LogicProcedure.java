/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.database.metadata;

import com.jiuqi.bi.database.metadata.LogicTable;
import org.json.JSONException;
import org.json.JSONObject;

public class LogicProcedure
implements Cloneable {
    private String name;
    private String description;
    private String owner;
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String OWNER = "owner";

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void toJson(JSONObject json) throws JSONException {
        json.put(NAME, (Object)this.name);
        json.put(DESCRIPTION, (Object)this.description);
        json.put(OWNER, (Object)this.owner);
    }

    public void fromJson(JSONObject json) throws JSONException {
        this.name = json.getString(NAME);
        this.description = json.getString(DESCRIPTION);
        this.owner = json.getString(OWNER);
    }

    public LogicTable clone() {
        try {
            return (LogicTable)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.toString());
        }
    }
}

