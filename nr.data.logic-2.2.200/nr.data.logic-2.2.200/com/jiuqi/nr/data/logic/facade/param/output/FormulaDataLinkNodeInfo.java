/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.data.logic.facade.param.output;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel(value="FormulaDataLinkNodeInfo", description="\u516c\u5f0f\u4e0e\u94fe\u63a5\u5173\u7cfb")
public final class FormulaDataLinkNodeInfo
implements Serializable {
    private static final long serialVersionUID = -7767997544545994626L;
    @ApiModelProperty(value="\u516c\u5f0f\u8868\u8fbe\u5f0f\u5f00\u59cb\u4f4d\u7f6e", name="beginIndex")
    private int beginIndex;
    @ApiModelProperty(value="\u516c\u5f0f\u8868\u8fbe\u5f0f\u7ed3\u675f\u4f4d\u7f6e", name="endIndex")
    private int endIndex;
    @ApiModelProperty(value="\u94fe\u63a5key", name="dataLinkKey")
    private String dataLinkKey;
    @ApiModelProperty(value="\u533a\u57dfkey", name="regionKey")
    private String regionKey;
    @ApiModelProperty(value="\u62a5\u8868key", name="formKey")
    private String formKey;

    public int getBeginIndex() {
        return this.beginIndex;
    }

    public void setBeginIndex(int beginIndex) {
        this.beginIndex = beginIndex;
    }

    public int getEndIndex() {
        return this.endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public String getDataLinkKey() {
        return this.dataLinkKey;
    }

    public void setDataLinkKey(String dataLinkKey) {
        this.dataLinkKey = dataLinkKey;
    }

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }
}

