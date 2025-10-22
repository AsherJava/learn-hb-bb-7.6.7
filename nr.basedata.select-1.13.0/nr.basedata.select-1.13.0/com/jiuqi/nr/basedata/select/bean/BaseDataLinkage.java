/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.basedata.select.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="BaseDataLinkage", description="\u57fa\u7840\u6570\u636e\u8054\u52a8\u914d\u7f6e")
public class BaseDataLinkage {
    @ApiModelProperty(value="\u524d\u4e00\u4e2a\u57fa\u7840\u6570\u636e\u7684key", name="preEntityKey")
    private String preEntityKey;
    @ApiModelProperty(value="\u524d\u4e00\u4e2a\u57fa\u7840\u6570\u636e\u7684\u503c", name="preValue")
    private String preValue;
    @ApiModelProperty(value="\u524d\u4e00\u4e2a\u57fa\u7840\u6570\u636e\u7684\u8fc7\u6ee4\u6761\u4ef6", name="preRowFilter")
    private String preRowFilter;
    @ApiModelProperty(value="\u524d\u4e00\u4e2a\u57fa\u7840\u6570\u636e\u7684\u5b9e\u4f53\u67e5\u8be2\u6743\u9650", name="preEntityAuth")
    private boolean preEntityAuth = true;
    @ApiModelProperty(value="\u524d\u4e00\u4e2a\u57fa\u7840\u6570\u636e\u662f\u5426\u4e3a\u591a\u9009", name="preEntityAuth")
    private boolean multipleSelect = false;

    public String getPreEntityKey() {
        return this.preEntityKey;
    }

    public void setPreEntityKey(String preEntityKey) {
        this.preEntityKey = preEntityKey;
    }

    public String getPreValue() {
        return this.preValue;
    }

    public void setPreValue(String preValue) {
        this.preValue = preValue;
    }

    public String getPreRowFilter() {
        return this.preRowFilter;
    }

    public void setPreRowFilter(String preRowFilter) {
        this.preRowFilter = preRowFilter;
    }

    public boolean isPreEntityAuth() {
        return this.preEntityAuth;
    }

    public void setPreEntityAuth(boolean preEntityAuth) {
        this.preEntityAuth = preEntityAuth;
    }

    public boolean isMultipleSelect() {
        return this.multipleSelect;
    }

    public void setMultipleSelect(boolean multipleSelect) {
        this.multipleSelect = multipleSelect;
    }
}

