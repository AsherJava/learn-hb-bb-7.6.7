/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.input;

import com.jiuqi.nr.jtable.params.base.JtableContext;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="FormulaCheckDesCopyInfo", description="\u5ba1\u6838\u51fa\u9519\u8bf4\u660e\u590d\u5236\u4fe1\u606f")
public class FormulaCheckDesCopyInfo {
    @ApiModelProperty(value="jtable\u73af\u5883\u4fe1\u606f(\u76ee\u6807\u73af\u5883)", name="context", required=true)
    private JtableContext context;
    @ApiModelProperty(value="\u6e90\u62a5\u8868\u65b9\u6848", name="sourceFormSchemeKey")
    private String sourceFormSchemeKey;
    @ApiModelProperty(value="\u6e90\u516c\u5f0f\u65b9\u6848", name="sourceFormulaSchemeKey")
    private String sourceFormulaSchemeKey;

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public String getSourceFormSchemeKey() {
        return this.sourceFormSchemeKey;
    }

    public void setSourceFormSchemeKey(String sourceFormSchemeKey) {
        this.sourceFormSchemeKey = sourceFormSchemeKey;
    }

    public String getSourceFormulaSchemeKey() {
        return this.sourceFormulaSchemeKey;
    }

    public void setSourceFormulaSchemeKey(String sourceFormulaSchemeKey) {
        this.sourceFormulaSchemeKey = sourceFormulaSchemeKey;
    }
}

