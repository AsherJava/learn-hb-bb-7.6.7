/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.invest.formula.function.rule.invest.compreequityratio;

import java.util.ArrayList;
import java.util.List;

public class InvestNodeInfo {
    private String unitId;
    private boolean wasVisited;
    private List<Integer> allVisitedList;
    private List<Integer> adjNodeList = new ArrayList<Integer>();

    public void setAllVisitedList(List<Integer> allVisitedList) {
        this.allVisitedList = allVisitedList;
    }

    public List<Integer> getAllVisitedList() {
        return this.allVisitedList;
    }

    public boolean WasVisited() {
        return this.wasVisited;
    }

    public void setWasVisited(boolean wasVisited) {
        this.wasVisited = wasVisited;
    }

    public InvestNodeInfo(String unitId) {
        this.unitId = unitId;
        this.wasVisited = false;
    }

    public void setVisited(int j) {
        this.allVisitedList.set(j, 1);
    }

    public String getUnitId() {
        return this.unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public List<Integer> getAdjNodeList() {
        return this.adjNodeList;
    }

    public void setAdjNodeList(List<Integer> adjNodeList) {
        this.adjNodeList = adjNodeList;
    }
}

