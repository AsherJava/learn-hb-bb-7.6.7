/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.designer.web.facade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.designer.web.facade.PlanResource;
import java.util.List;
import java.util.Map;

public class ServicePlan {
    @JsonProperty
    private Map<String, PlanResource> PlanResource;
    @JsonProperty
    private List<String> RestrictFuncs;

    @JsonIgnore
    public Map<String, PlanResource> getPlanResource() {
        return this.PlanResource;
    }

    @JsonIgnore
    public void setPlanResource(Map<String, PlanResource> planResource) {
        this.PlanResource = planResource;
    }

    @JsonIgnore
    public List<String> getRestrictFuncs() {
        return this.RestrictFuncs;
    }

    @JsonIgnore
    public void setRestrictFuncs(List<String> restrictFuncs) {
        this.RestrictFuncs = restrictFuncs;
    }
}

