/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.monitor.api.vo.show;

public class MonitorTableColumnVO {
    private String prop;
    private String label;

    public MonitorTableColumnVO() {
    }

    public MonitorTableColumnVO(String prop, String label) {
        this.prop = prop;
        this.label = label;
    }

    public String getProp() {
        return this.prop;
    }

    public void setProp(String prop) {
        this.prop = prop;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}

