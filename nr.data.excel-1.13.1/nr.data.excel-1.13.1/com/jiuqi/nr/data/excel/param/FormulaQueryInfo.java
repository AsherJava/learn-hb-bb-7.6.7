/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTAdjustor
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.data.excel.param;

import com.jiuqi.bi.syntax.ast.IASTAdjustor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

@ApiModel(value="FormulaQueryInfo", description="\u516c\u5f0f\u67e5\u8be2\u53c2\u6570")
public class FormulaQueryInfo {
    private String formSchemeKey;
    private String formulaSchemeKey;
    @ApiModelProperty(value="\u62a5\u8868key", name="formKey")
    private String formKey;
    @ApiModelProperty(value="\u94fe\u63a5key", name="dataLinkKey")
    private String dataLinkKey;
    @ApiModelProperty(value="\u516c\u5f0f\u7c7b\u578b", name="useType")
    private String useType;
    @ApiModelProperty(value="\u516c\u5f0f\u663e\u793a\u7c7b\u578b\uff08 @see com.jiuqi.np.dataengine.common.DataEngineConsts.FormulaShowType\uff09", name="showType")
    private int showType;
    @ApiModelProperty(value="\u4f20\u9012\u516c\u5f0f\u8c03\u6574\u4fe1\u606f\uff0c\u6682\u65f6\u53ea\u6709excel\u516c\u5f0f\u4f7f\u7528", name="adjustorList")
    private List<IASTAdjustor> adjustorList;

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getDataLinkKey() {
        return this.dataLinkKey;
    }

    public void setDataLinkKey(String dataLinkKey) {
        this.dataLinkKey = dataLinkKey;
    }

    public String getUseType() {
        return this.useType;
    }

    public void setUseType(String useType) {
        this.useType = useType;
    }

    public int getShowType() {
        return this.showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }

    public List<IASTAdjustor> getAdjustorList() {
        return this.adjustorList;
    }

    public void setAdjustorList(List<IASTAdjustor> adjustorList) {
        this.adjustorList = adjustorList;
    }
}

