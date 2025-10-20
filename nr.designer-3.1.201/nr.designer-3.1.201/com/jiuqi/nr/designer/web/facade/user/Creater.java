/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.designer.web.facade.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Creater {
    @JsonProperty
    private String FullName;
    @JsonProperty
    private String Id;

    @JsonIgnore
    public String getFullName() {
        return this.FullName;
    }

    @JsonIgnore
    public void setFullName(String fullName) {
        this.FullName = fullName;
    }

    @JsonIgnore
    public String getId() {
        return this.Id;
    }

    @JsonIgnore
    public void setId(String id) {
        this.Id = id;
    }
}

