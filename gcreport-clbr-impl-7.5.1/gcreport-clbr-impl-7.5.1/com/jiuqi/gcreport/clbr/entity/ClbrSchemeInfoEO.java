/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.gcreport.clbr.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class ClbrSchemeInfoEO {
    @JsonProperty(index=1)
    private String clbrTypes;
    @JsonProperty(index=2)
    private String relations;
    @JsonProperty(index=3)
    private String oppRelations;
    @JsonProperty(index=4)
    private String oppClbrTypes;

    public String getClbrTypes() {
        return this.clbrTypes;
    }

    public void setClbrTypes(String clbrTypes) {
        this.clbrTypes = clbrTypes;
    }

    public String getRelations() {
        return this.relations;
    }

    public void setRelations(String relations) {
        this.relations = relations;
    }

    public String getOppRelations() {
        return this.oppRelations;
    }

    public void setOppRelations(String oppRelations) {
        this.oppRelations = oppRelations;
    }

    public String getOppClbrTypes() {
        return this.oppClbrTypes;
    }

    public void setOppClbrTypes(String oppClbrTypes) {
        this.oppClbrTypes = oppClbrTypes;
    }
}

