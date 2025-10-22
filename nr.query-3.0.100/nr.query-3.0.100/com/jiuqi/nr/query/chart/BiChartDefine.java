/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 */
package com.jiuqi.nr.query.chart;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jiuqi.nr.query.chart.BiChartDefineDeserializer;

@JsonDeserialize(using=BiChartDefineDeserializer.class)
public class BiChartDefine {
    private String guid;
    private String title;
    private String securityLevel;

    public String getGuid() {
        return this.guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSecurityLevel() {
        return this.securityLevel;
    }

    public void setSecurityLevel(String securityLevel) {
        this.securityLevel = securityLevel;
    }
}

