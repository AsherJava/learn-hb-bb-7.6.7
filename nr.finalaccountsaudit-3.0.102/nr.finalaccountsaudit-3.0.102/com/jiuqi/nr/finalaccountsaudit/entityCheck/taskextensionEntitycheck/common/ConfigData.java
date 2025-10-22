/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

@Component
public class ConfigData {
    @JsonProperty(value="XBYS")
    private String XBYS;
    @JsonProperty(value="DWYSJC")
    private String DWYSJC;
    @JsonProperty(value="JSYY")
    private String JSYY;
    @JsonProperty(value="SNDM")
    private String SNDM;
    @JsonProperty(value="DWDM")
    private String DWDM;
    @JsonProperty(value="BBLX")
    private String BBLX;
    @JsonProperty(value="SNBBLX")
    private String SNBBLX;

    public String getXBYS() {
        return this.XBYS;
    }

    public void setXBYS(String XBYS) {
        this.XBYS = XBYS;
    }

    public String getDWYSJC() {
        return this.DWYSJC;
    }

    public void setDWYSJC(String DWYSJC) {
        this.DWYSJC = DWYSJC;
    }

    public String getJSYY() {
        return this.JSYY;
    }

    public void setJSYY(String JSYY) {
        this.JSYY = JSYY;
    }

    public String getSNDM() {
        return this.SNDM;
    }

    public void setSNDM(String SNDM) {
        this.SNDM = SNDM;
    }

    public String getDWDM() {
        return this.DWDM;
    }

    public void setDWDM(String DWDM) {
        this.DWDM = DWDM;
    }

    public String getBBLX() {
        return this.BBLX;
    }

    public void setBBLX(String BBLX) {
        this.BBLX = BBLX;
    }

    public String getSNBBLX() {
        return this.SNBBLX;
    }

    public void setSNBBLX(String SNBBLX) {
        this.SNBBLX = SNBBLX;
    }
}

