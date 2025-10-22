/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.annotation.input;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="\u67e5\u8be2\u5355\u5143\u683c\u6279\u6ce8\u7684\u8bf7\u6c42\u4fe1\u606f", description="\u67e5\u8be2\u5355\u5143\u683c\u6279\u6ce8\u7684\u8bf7\u6c42\u4fe1\u606f")
public class CellAnnotationQueryInfo {
    @ApiModelProperty(value="\u62a5\u8868\u65b9\u6848Key", name="formSchemeKey", required=true)
    private String formSchemeKey;
    @ApiModelProperty(value="\u7ef4\u5ea6\u4fe1\u606f", name="dimensionCombination", required=true)
    private DimensionCombination dimensionCombination;
    @ApiModelProperty(value="\u6570\u636e\u94fe\u63a5", name="dataLinkKey", required=true)
    private String dataLinkKey;
    @ApiModelProperty(value="\u6d6e\u52a8\u884cid", name="rowId")
    private String rowId;
    @ApiModelProperty(value="\u5f53\u524d\u767b\u5f55\u7528\u6237name", name="userName", required=true)
    private String userName;

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public DimensionCombination getDimensionCombination() {
        return this.dimensionCombination;
    }

    public void setDimensionCombination(DimensionCombination dimensionCombination) {
        this.dimensionCombination = dimensionCombination;
    }

    public String getDataLinkKey() {
        return this.dataLinkKey;
    }

    public void setDataLinkKey(String dataLinkKey) {
        this.dataLinkKey = dataLinkKey;
    }

    public String getRowId() {
        return this.rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

