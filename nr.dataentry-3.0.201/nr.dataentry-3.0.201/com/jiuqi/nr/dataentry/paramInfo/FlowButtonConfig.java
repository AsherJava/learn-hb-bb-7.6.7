/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.JsonObject
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.dataentry.paramInfo;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jiuqi.nr.dataentry.paramInfo.FlowButtonInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="FlowButtonConfig", description="\u6d41\u7a0b\u6309\u94ae\u914d\u7f6e")
public class FlowButtonConfig {
    @ApiModelProperty(value="\u4e0a\u62a5\u6309\u94ae", name="act_upload")
    private FlowButtonInfo act_upload;
    @ApiModelProperty(value="\u9000\u56de\u6309\u94ae", name="act_reject")
    private FlowButtonInfo act_reject;
    @ApiModelProperty(value="\u9001\u5ba1\u6309\u94ae", name="act_submit")
    private FlowButtonInfo act_submit;
    @ApiModelProperty(value="\u9000\u5ba1\u6309\u94ae", name="act_return")
    private FlowButtonInfo act_return;
    @ApiModelProperty(value="\u786e\u8ba4\u6309\u94ae", name="act_confirm")
    private FlowButtonInfo act_confirm;

    public FlowButtonConfig(String defaultButtonNameConfig) {
        Gson gson = new Gson();
        JsonObject jsonObj = (JsonObject)gson.fromJson(defaultButtonNameConfig, JsonObject.class);
        this.act_upload = new FlowButtonInfo(jsonObj.getAsJsonObject("shangbao"), "act_upload");
        this.act_reject = new FlowButtonInfo(jsonObj.getAsJsonObject("tuihui"), "act_reject");
        this.act_submit = new FlowButtonInfo(jsonObj.getAsJsonObject("songshen"), "act_submit");
        this.act_return = new FlowButtonInfo(jsonObj.getAsJsonObject("tuishen"), "act_return");
        this.act_confirm = new FlowButtonInfo(jsonObj.getAsJsonObject("queren"), "act_confirm");
    }

    public FlowButtonInfo getAct_upload() {
        return this.act_upload;
    }

    public void setAct_upload(FlowButtonInfo act_upload) {
        this.act_upload = act_upload;
    }

    public FlowButtonInfo getAct_reject() {
        return this.act_reject;
    }

    public void setAct_reject(FlowButtonInfo act_reject) {
        this.act_reject = act_reject;
    }

    public FlowButtonInfo getAct_submit() {
        return this.act_submit;
    }

    public void setAct_submit(FlowButtonInfo act_submit) {
        this.act_submit = act_submit;
    }

    public FlowButtonInfo getAct_return() {
        return this.act_return;
    }

    public void setAct_return(FlowButtonInfo act_return) {
        this.act_return = act_return;
    }

    public FlowButtonInfo getAct_confirm() {
        return this.act_confirm;
    }

    public void setAct_confirm(FlowButtonInfo act_confirm) {
        this.act_confirm = act_confirm;
    }
}

