/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.bi.quickreport.model;

import java.io.Serializable;
import org.json.JSONObject;

public class ConditionStyle
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private String condition;
    private String comment;
    private String description;
    private static final String CS_CONDITION = "condition";
    private static final String CS_COMMENT = "comment";
    private static final String CS_DESCRIPTION = "description";

    public String getCondition() {
        return this.condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put(CS_CONDITION, (Object)this.condition);
        json.put(CS_COMMENT, (Object)this.comment);
        json.put(CS_DESCRIPTION, (Object)this.description);
        return json;
    }

    public void fromJSON(JSONObject json) {
        this.condition = json.optString(CS_CONDITION);
        this.comment = json.optString(CS_COMMENT);
        this.description = json.optString(CS_DESCRIPTION);
    }

    public String toString() {
        return "[" + this.condition + "]";
    }
}

