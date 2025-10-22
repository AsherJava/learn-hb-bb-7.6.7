/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.FormulaSchemeData
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.dataentry.bean.ReviewInfoParam;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.FormulaSchemeData;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class BatchCheckParam
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="\u7cfb\u7edf\u914d\u7f6e", name="sysParam")
    private Map<String, Object> sysParam;
    @ApiModelProperty(value="\u4e3b\u4f53\u7ef4\u5ea6", name="entityList")
    private List<EntityViewData> entityList;
    @ApiModelProperty(value="\u516c\u5f0f\u65b9\u6848\u5217\u8868", name="formulaSchemeList")
    private List<FormulaSchemeData> formulaSchemeList;
    private ReviewInfoParam reviewInfoParam;

    public List<FormulaSchemeData> getFormulaSchemeList() {
        return this.formulaSchemeList;
    }

    public void setFormulaSchemeList(List<FormulaSchemeData> formulaSchemeList) {
        this.formulaSchemeList = formulaSchemeList;
    }

    public ReviewInfoParam getReviewInfoParam() {
        return this.reviewInfoParam;
    }

    public void setReviewInfoParam(ReviewInfoParam reviewInfoParam) {
        this.reviewInfoParam = reviewInfoParam;
    }

    public Map<String, Object> getSysParam() {
        return this.sysParam;
    }

    public void setSysParam(Map<String, Object> sysParam) {
        this.sysParam = sysParam;
    }

    public List<EntityViewData> getEntityList() {
        return this.entityList;
    }

    public void setEntityList(List<EntityViewData> entityList) {
        this.entityList = entityList;
    }
}

