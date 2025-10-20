/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.bi.office.excel.print;

import java.io.Serializable;
import org.json.JSONObject;

public final class PageStart
implements Serializable,
Cloneable {
    private static final long serialVersionUID = -4430136536180089950L;
    private boolean enable;
    private int pageNum;

    public boolean isEnable() {
        return this.enable;
    }

    public int getPageNum() {
        return this.pageNum;
    }

    public JSONObject toJson() {
        JSONObject json_pageStart = new JSONObject();
        json_pageStart.put("enable", this.enable);
        json_pageStart.put("pageNum", this.pageNum);
        return json_pageStart;
    }

    public void fromJson(JSONObject json_pageStart) {
        this.enable = json_pageStart.optBoolean("enable");
        this.pageNum = json_pageStart.optInt("pageNum");
    }

    protected Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}

