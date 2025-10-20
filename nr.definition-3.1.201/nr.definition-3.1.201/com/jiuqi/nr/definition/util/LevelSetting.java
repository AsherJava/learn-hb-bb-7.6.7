/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 */
package com.jiuqi.nr.definition.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true)
public class LevelSetting
implements Serializable {
    private static final long serialVersionUID = -6470407901995656463L;
    private int type;
    private String code;
    private String precision;

    public LevelSetting() {
    }

    public LevelSetting(int type, String code, String precision) {
        this.type = type;
        this.code = code;
        this.precision = precision;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{").append("\"type\":").append(this.type).append(", \"code\":\"").append(this.code).append("\"").append(", \"precision\":\"").append(this.precision == null ? "" : this.precision).append("\"}");
        return sb.toString();
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPrecision() {
        return this.precision;
    }

    public void setPrecision(String precision) {
        this.precision = precision;
    }
}

