/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.monitor.api.vo.execute;

import java.util.List;

public class MonitorExeSchemeVO {
    private String recid;
    private String code;
    private String label;
    private String title;
    private List<String> nrScheme;
    private String unitType;
    private List<String> monitor;
    private List<String> showNode;

    public String getRecid() {
        return this.recid;
    }

    public void setRecid(String recid) {
        this.recid = recid;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<String> getNrScheme() {
        return this.nrScheme;
    }

    public void setNrScheme(List<String> nrScheme) {
        this.nrScheme = nrScheme;
    }

    public String getUnitType() {
        return this.unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public List<String> getMonitor() {
        return this.monitor;
    }

    public void setMonitor(List<String> monitor) {
        this.monitor = monitor;
    }

    public List<String> getShowNode() {
        return this.showNode;
    }

    public void setShowNode(List<String> showNode) {
        this.showNode = showNode;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

