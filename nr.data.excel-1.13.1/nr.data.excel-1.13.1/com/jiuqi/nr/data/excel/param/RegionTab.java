/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.nr.definition.facade.RegionTabSettingDefine
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.data.excel.param;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.nr.definition.facade.RegionTabSettingDefine;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="RegionTab", description="\u5206\u9875\u7b7e\u5c5e\u6027")
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class RegionTab {
    @ApiModelProperty(value="\u5206\u9875\u7b7e\u6807\u9898", name="title")
    private String title;
    @ApiModelProperty(value="\u5206\u9875\u7b7e\u663e\u793a\u6761\u4ef6", name="disp")
    private String disp;
    @ApiModelProperty(value="\u5206\u9875\u7b7e\u6570\u636e\u8fc7\u6ee4\u6761\u4ef6", name="filter")
    private String filter;
    @ApiModelProperty(value="\u5206\u9875\u7b7e\u7ef4\u503c\u8868\u8fbe\u5f0f", name="exp")
    private String exp;

    public RegionTab(RegionTabSettingDefine regionTabSettingDefine) {
        this.title = regionTabSettingDefine.getTitle();
        this.disp = regionTabSettingDefine.getDisplayCondition();
        this.filter = regionTabSettingDefine.getFilterCondition();
        this.exp = regionTabSettingDefine.getBindingExpression();
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDisp() {
        return this.disp;
    }

    public void setDisp(String disp) {
        this.disp = disp;
    }

    public String getFilter() {
        return this.filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getExp() {
        return this.exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }
}

