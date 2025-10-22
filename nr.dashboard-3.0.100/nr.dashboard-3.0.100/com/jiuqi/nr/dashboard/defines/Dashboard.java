/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 */
package com.jiuqi.nr.dashboard.defines;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Dashboard {
    private String guid;
    private String parentGuid;
    private String title;
    private String securityLevel;
    @JsonIgnore
    private String modifyTime;
    @JsonIgnore
    private String creater;
    @JsonIgnore
    private String description;
    @JsonIgnore
    private String divMode;
    @JsonIgnore
    private String version;

    public String getGuid() {
        return this.guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getParentGuid() {
        return this.parentGuid;
    }

    public void setParentGuid(String parentGuid) {
        this.parentGuid = parentGuid;
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

    public String getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getCreater() {
        return this.creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDivMode() {
        return this.divMode;
    }

    public void setDivMode(String divMode) {
        this.divMode = divMode;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}

