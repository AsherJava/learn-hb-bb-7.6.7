/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.params.output;

import com.jiuqi.nr.jtable.params.output.Info;
import java.util.ArrayList;
import java.util.List;

public class LevelUploadInfo {
    private int total;
    private String action;
    private String parId;
    private String parCode;
    private String parName;
    private List<Info> unitInfo = new ArrayList<Info>();

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Info> getUnitInfo() {
        return this.unitInfo;
    }

    public void setUnitInfo(List<Info> unitInfo) {
        this.unitInfo = unitInfo;
    }

    public String getParId() {
        return this.parId;
    }

    public void setParId(String parId) {
        this.parId = parId;
    }

    public String getParCode() {
        return this.parCode;
    }

    public void setParCode(String parCode) {
        this.parCode = parCode;
    }

    public String getParName() {
        return this.parName;
    }

    public void setParName(String parName) {
        this.parName = parName;
    }
}

