/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.database.metadata;

import com.jiuqi.bi.database.metadata.LogicTable;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class LogicForeignKey
implements Cloneable {
    private String foreignKeyName;
    private String subTableName;
    private List<String> subColumnName;
    private String mainTableName;
    private List<String> mainColumnName;
    private static final String FOREIGNKEYNAME = "foreignKeyName";
    private static final String SUBTABLENAME = "subTableName";
    private static final String SUBCOLUMNNAME = "subColumnName";
    private static final String MAINTABLENAME = "mainTableName";
    private static final String MAINCOLUMNNAME = "mainColumnName";

    public LogicForeignKey() {
    }

    public LogicForeignKey(String foreignKeyName, String subTableName, List<String> subColumnName, String mainTableName, List<String> mainColumnName) {
        this.foreignKeyName = foreignKeyName;
        this.subTableName = subTableName;
        this.subColumnName = subColumnName;
        this.mainTableName = mainTableName;
        this.mainColumnName = mainColumnName;
    }

    public String getForeignKeyName() {
        return this.foreignKeyName;
    }

    public void setForeignKeyName(String foreignKeyName) {
        this.foreignKeyName = foreignKeyName;
    }

    public String getSubTableName() {
        return this.subTableName;
    }

    public void setSubTableName(String subTableName) {
        this.subTableName = subTableName;
    }

    public List<String> getSubColumnName() {
        return this.subColumnName;
    }

    public void setSubColumnName(List<String> subColumnName) {
        this.subColumnName = subColumnName;
    }

    public String getMainTableName() {
        return this.mainTableName;
    }

    public void setMainTableName(String mainTableName) {
        this.mainTableName = mainTableName;
    }

    public List<String> getMainColumnName() {
        return this.mainColumnName;
    }

    public void setMainColumnName(List<String> mainColumnName) {
        this.mainColumnName = mainColumnName;
    }

    public void toJson(JSONObject json) throws JSONException {
        json.put(FOREIGNKEYNAME, (Object)this.foreignKeyName);
        json.put(SUBTABLENAME, (Object)this.subTableName);
        json.put(SUBCOLUMNNAME, this.subColumnName);
        json.put(MAINTABLENAME, (Object)this.mainTableName);
        json.put(MAINCOLUMNNAME, this.mainColumnName);
    }

    public int hashCode() {
        int hash = 0;
        if (this.foreignKeyName != null) {
            hash += this.foreignKeyName.hashCode();
        }
        if (this.subTableName != null) {
            hash += this.subTableName.hashCode();
        }
        if (this.subColumnName != null) {
            hash += this.subColumnName.hashCode();
        }
        if (this.mainTableName != null) {
            hash += this.mainTableName.hashCode();
        }
        if (this.mainColumnName != null) {
            hash += this.mainColumnName.hashCode();
        }
        return hash;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        LogicForeignKey another = (LogicForeignKey)obj;
        return this.foreignKeyName.equals(another.getForeignKeyName());
    }

    public LogicTable clone() {
        try {
            return (LogicTable)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.toString());
        }
    }

    public String toString() {
        return "LogicForeignKey{\u3010foreignKeyName=" + this.foreignKeyName + '\u3011' + ",\u3010subTableName=" + this.subTableName + '\u3011' + ",\u3010subColumnName=" + this.subColumnName.toString() + '\u3011' + ",\u3010mainTableName=" + this.mainTableName + '\u3011' + ",\u3010mainColumnName=" + this.mainColumnName.toString() + '\u3011' + '}';
    }
}

