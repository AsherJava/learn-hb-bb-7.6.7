/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.bi.office.excel.print;

import com.jiuqi.bi.office.excel.print.HeaderFooter;
import java.io.Serializable;
import org.json.JSONObject;

public final class DifferentFirst
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 3208384059926347195L;
    private boolean enable;
    private HeaderFooter first;

    public boolean isEnable() {
        return this.enable;
    }

    public HeaderFooter getFirst() {
        return this.first;
    }

    public JSONObject toJson() {
        JSONObject json_differentFirst = new JSONObject();
        json_differentFirst.put("enable", this.enable);
        if (this.first != null) {
            json_differentFirst.put("first", (Object)this.first.toJson());
        }
        return json_differentFirst;
    }

    public void fromJson(JSONObject json_differentFirst) {
        this.enable = json_differentFirst.optBoolean("enable");
        this.first = null;
        JSONObject json_first = json_differentFirst.optJSONObject("first");
        if (json_first != null) {
            this.first = new HeaderFooter();
            this.first.fromJson(json_first);
        }
    }

    protected Object clone() {
        try {
            DifferentFirst cloned = (DifferentFirst)super.clone();
            cloned.first = this.first == null ? null : (HeaderFooter)this.first.clone();
            return cloned;
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}

