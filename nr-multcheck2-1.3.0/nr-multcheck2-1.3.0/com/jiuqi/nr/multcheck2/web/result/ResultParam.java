/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.multcheck2.web.result;

import com.jiuqi.nr.multcheck2.web.vo.MCLabel;
import java.util.ArrayList;
import java.util.List;

public class ResultParam {
    private String task;
    private String period;
    private MCLabel org;
    private List<MCLabel> dimList;
    private String scheme;
    private int executeCount;
    private int unboundCount;

    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public MCLabel getOrg() {
        return this.org;
    }

    public void setOrg(MCLabel org) {
        this.org = org;
    }

    public List<MCLabel> getDimList() {
        return this.dimList;
    }

    public void setDimList(List<MCLabel> dimList) {
        this.dimList = dimList;
    }

    public void addDimList(MCLabel label) {
        if (this.dimList == null) {
            this.dimList = new ArrayList<MCLabel>();
        }
        this.dimList.add(label);
    }

    public String getScheme() {
        return this.scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public int getExecuteCount() {
        return this.executeCount;
    }

    public void setExecuteCount(int executeCount) {
        this.executeCount = executeCount;
    }

    public int getUnboundCount() {
        return this.unboundCount;
    }

    public void setUnboundCount(int unboundCount) {
        this.unboundCount = unboundCount;
    }
}

