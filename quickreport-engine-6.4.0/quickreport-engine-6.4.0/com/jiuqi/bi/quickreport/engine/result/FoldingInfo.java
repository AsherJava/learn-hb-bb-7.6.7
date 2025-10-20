/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.bi.quickreport.engine.result;

import com.jiuqi.bi.quickreport.QuickReportError;
import java.io.Serializable;
import org.json.JSONObject;

public class FoldingInfo
implements Cloneable,
Serializable {
    private static final long serialVersionUID = 1L;
    private boolean expanding = true;
    private int startNum;
    private int endNum;
    private static final String FI_EXPANDING = "expanding";
    private static final String FI_STARTNUM = "startNum";
    private static final String FI_ENDNUM = "endNum";

    public FoldingInfo() {
    }

    public FoldingInfo(int startNum, int endNum) {
        this();
        this.startNum = startNum;
        this.endNum = endNum;
    }

    public boolean isExpanding() {
        return this.expanding;
    }

    public void setExpanding(boolean expanding) {
        this.expanding = expanding;
    }

    public int getStartNum() {
        return this.startNum;
    }

    public void setStartNum(int startNum) {
        this.startNum = startNum;
    }

    public int getEndNum() {
        return this.endNum;
    }

    public void setEndNum(int endNum) {
        this.endNum = endNum;
    }

    public boolean isLeaf() {
        return this.startNum <= 0 || this.endNum <= 0;
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put(FI_EXPANDING, this.expanding);
        json.put(FI_STARTNUM, this.startNum);
        json.put(FI_ENDNUM, this.endNum);
        return json;
    }

    public void fromJSON(JSONObject json) {
        this.expanding = json.optBoolean(FI_EXPANDING);
        this.startNum = json.optInt(FI_STARTNUM);
        this.endNum = json.optInt(FI_ENDNUM);
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(this.expanding ? (char)'+' : '-').append('[').append(this.startNum).append('~').append(this.endNum).append(']');
        return buffer.toString();
    }

    public FoldingInfo clone() {
        try {
            return (FoldingInfo)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new QuickReportError(e);
        }
    }
}

