/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.database.metadata;

import org.json.JSONException;
import org.json.JSONObject;

public class LogicTable
implements Cloneable {
    private String name;
    private String description;
    private String owner;
    private int type;
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String OWNER = "owner";
    private static final String TYPE = "type";
    public static final int LOGICTABLETYPE_TABLE = 1;
    public static final int LOGICTABLETYPE_VIEW = 2;
    public static final int LOGICTABLETYPE_SYNONYM = 4;

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

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void toJson(JSONObject json) throws JSONException {
        json.put(NAME, (Object)this.name);
        json.put(DESCRIPTION, this.description == null ? JSONObject.NULL : this.description);
        json.put(OWNER, (Object)this.owner);
        json.put(TYPE, this.type);
    }

    public void fromJson(JSONObject json) throws JSONException {
        this.name = json.getString(NAME);
        this.description = json.optString(DESCRIPTION, null);
        this.owner = json.getString(OWNER);
        this.type = json.getInt(TYPE);
    }

    public LogicTable clone() {
        try {
            return (LogicTable)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.toString());
        }
    }

    public int hashCode() {
        int hash = 0;
        if (this.owner != null) {
            hash += this.owner.hashCode();
        }
        if (this.name != null) {
            hash += this.name.hashCode() * 100;
        }
        return hash;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof LogicTable)) {
            return false;
        }
        LogicTable t = (LogicTable)obj;
        return this.isEqual(this.owner, t.owner) && this.isEqual(this.name, t.name);
    }

    private boolean isEqual(String s1, String s2) {
        if (s1 == null) {
            return s2 == null;
        }
        return s2 != null && s1.equals(s2);
    }
}

