/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.Region
 *  org.json.JSONObject
 */
package com.jiuqi.bi.quickreport.engine.result;

import com.jiuqi.bi.quickreport.QuickReportError;
import com.jiuqi.bi.syntax.cell.Region;
import java.io.Serializable;
import org.json.JSONObject;

public class PagingInfo
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    public static final String ID_GOLOBAL = "@GLOBAL";
    public static final String ID_DEFAULT = "@DEFAULT";
    private String id;
    private Region region;
    private int pageSize;
    private int pageNum;
    private int pageCount;

    public PagingInfo(String id) {
        this.id = id;
        this.pageCount = -1;
        this.pageNum = -1;
        this.pageSize = -1;
    }

    public String getId() {
        return this.id;
    }

    public Region getRegion() {
        return this.region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageCount() {
        return this.pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", (Object)this.id);
        if (this.region != null) {
            JSONObject jsonRegion = new JSONObject();
            jsonRegion.put("str", (Object)this.region.toString());
            jsonRegion.put("top", this.region.top() - 1);
            jsonRegion.put("left", this.region.left() - 1);
            jsonRegion.put("bottom", this.region.bottom() - 1);
            jsonRegion.put("right", this.region.right() - 1);
            json.put("region", (Object)jsonRegion);
        }
        json.put("pageSize", this.pageSize);
        json.put("pageNum", this.pageNum);
        json.put("pageCount", this.pageCount);
        return json;
    }

    public void fromJson(JSONObject json) {
        this.id = json.optString("id");
        this.region = json.has("region") ? new Region(json.optString("region")) : null;
        this.pageSize = json.optInt("pageSize");
        this.pageNum = json.optInt("pageNum");
        this.pageCount = json.optInt("pageCount");
    }

    public PagingInfo clone() {
        try {
            return (PagingInfo)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new QuickReportError(e);
        }
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append('[').append(this.id);
        if (this.region != null) {
            buffer.append(", ").append(this.region);
        }
        buffer.append(", pageNum = ").append(this.pageNum).append(", pageCount = ").append(this.pageCount).append(", pageSize = ").append(this.pageSize).append(']');
        return buffer.toString();
    }
}

