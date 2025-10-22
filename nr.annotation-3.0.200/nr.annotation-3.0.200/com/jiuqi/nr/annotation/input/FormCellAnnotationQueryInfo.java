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

@ApiModel(value="\u67e5\u8be2\u8868\u5355\u4e0b\u6709\u6279\u6ce8\u7684\u5355\u5143\u683c\u7684\u8bf7\u6c42\u4fe1\u606f", description="\u67e5\u8be2\u8868\u5355\u4e0b\u6709\u6279\u6ce8\u7684\u5355\u5143\u683c\u7684\u8bf7\u6c42\u4fe1\u606f")
public class FormCellAnnotationQueryInfo {
    @ApiModelProperty(value="\u62a5\u8868\u65b9\u6848Key", name="formSchemeKey", required=true)
    private String formSchemeKey;
    @ApiModelProperty(value="\u62a5\u8868Key", name="formKey", required=true)
    private String formKey;
    @ApiModelProperty(value="\u7ef4\u5ea6\u4fe1\u606f", name="dimensionCombination", required=true)
    private DimensionCombination dimensionCombination;
    @ApiModelProperty(value="\u6570\u636e\u533a\u57dfkey", name="regionKey", required=true)
    private String regionKey;

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public DimensionCombination getDimensionCombination() {
        return this.dimensionCombination;
    }

    public void setDimensionCombination(DimensionCombination dimensionCombination) {
        this.dimensionCombination = dimensionCombination;
    }

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }
}

