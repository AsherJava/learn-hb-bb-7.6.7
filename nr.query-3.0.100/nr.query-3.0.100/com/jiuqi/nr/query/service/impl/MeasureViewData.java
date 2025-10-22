/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.query.service.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="MeasureViewData", description="\u91cf\u7eb2\u89c6\u56fe\u4fe1\u606f")
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class MeasureViewData {
    @ApiModelProperty(value="\u91cf\u7eb2\u89c6\u56fekey", name="key")
    private String key;
    @ApiModelProperty(value="\u91cf\u7eb2\u89c6\u56fe\u6807\u9898", name="title")
    private String title;
    @ApiModelProperty(value="\u91cf\u7eb2\u89c6\u56fe\u7c7b\u578b\uff0c\u53c2\u7167\uff08com.jiuqi.np.definition.common.TableKind\uff09", name="kind")
    private int kind;
    @ApiModelProperty(value="\u91cf\u7eb2\u89c6\u56fe\u77ed\u6807\u9898", name="titleAbbreviation")
    private String titleAbbreviation;
    @ApiModelProperty(value="\u91cf\u7eb2\u89c6\u56fe\u5173\u8054\u7684tablekey", name="tableKey")
    private String tableKey;
    @ApiModelProperty(value="\u91cf\u7eb2\u89c6\u56fe\u5173\u8054\u7684tablename", name="tableName")
    private String tableName;
    @ApiModelProperty(value="\u91cf\u7eb2\u89c6\u56fe\u5206\u7ec4Code", name="groupCode")
    private String groupCode;
    @ApiModelProperty(value="\u91cf\u7eb2\u89c6\u56fe\u5206\u7ec4Name", name="groupName")
    private String groupName;

    public MeasureViewData(EntityViewDefine entityViewDefine, TableModelDefine entityTableDefine) {
        this.key = entityViewDefine.getEntityId();
        this.title = entityTableDefine.getTitle();
        this.kind = entityTableDefine.getKind().getValue();
        this.tableKey = entityTableDefine.getKeys();
        this.tableName = entityTableDefine.getName();
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

    public int getKind() {
        return this.kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public String getTitleAbbreviation() {
        return this.titleAbbreviation;
    }

    public void setTitleAbbreviation(String titleAbbreviation) {
        this.titleAbbreviation = titleAbbreviation;
    }

    public String getTableKey() {
        return this.tableKey;
    }

    public void setTableKey(String tableKey) {
        this.tableKey = tableKey;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
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

