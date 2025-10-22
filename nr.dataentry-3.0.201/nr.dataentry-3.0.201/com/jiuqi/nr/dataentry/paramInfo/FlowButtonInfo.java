/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.dataentry.paramInfo;

import com.google.gson.JsonObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="FlowButtonInfo", description="\u6d41\u7a0b\u6309\u94ae\u4fe1\u606f")
public class FlowButtonInfo {
    @ApiModelProperty(value="\u6309\u94aeId", name="id")
    private String id;
    @ApiModelProperty(value="\u6309\u94ae\u540d\u79f0", name="title")
    private String name;
    @ApiModelProperty(value="\u6309\u94ae\u91cd\u547d\u540d", name="reTitle")
    private String reName;
    @ApiModelProperty(value="\u6309\u94ae\u72b6\u6001\u540d\u79f0", name="stateTitle")
    private String stateName;

    public FlowButtonInfo(JsonObject jsonObject, String actionId) {
        this.id = actionId;
        this.name = jsonObject.get("name").getAsString();
        this.reName = jsonObject.get("rename").getAsString();
        this.stateName = jsonObject.get("statename").getAsString();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReName() {
        return this.reName;
    }

    public void setReName(String reName) {
        this.reName = reName;
    }

    public String getStateName() {
        return this.stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}

