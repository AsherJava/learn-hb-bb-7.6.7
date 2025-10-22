/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="MeasureViewData", description="\u91cf\u7eb2\u4fe1\u606f")
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class MeasureViewData {
    @ApiModelProperty(value="\u91cf\u7eb2key", name="key")
    private String key;
    @ApiModelProperty(value="\u91cf\u7eb2\u6807\u9898", name="title")
    private String title;
    @ApiModelProperty(value="\u91cf\u7eb2code", name="code")
    private String code;
    @ApiModelProperty(value="\u91cf\u7eb2name", name="name")
    private String name;
    @ApiModelProperty(value="\u91cf\u7eb2\u89c6\u56fe\u5206\u7ec4Code", name="groupCode")
    private String groupCode;
    @ApiModelProperty(value="\u91cf\u7eb2\u89c6\u56fe\u5206\u7ec4Name", name="groupName")
    private String groupName;

    public MeasureViewData(TableModelDefine tableModelDefine) {
        this.key = tableModelDefine.getID();
        this.title = tableModelDefine.getTitle();
        this.code = tableModelDefine.getCode();
        this.name = tableModelDefine.getName();
        this.groupCode = "RENMINBI";
        this.groupName = "\u4eba\u6c11\u5e01";
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupCode() {
        return this.groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}

