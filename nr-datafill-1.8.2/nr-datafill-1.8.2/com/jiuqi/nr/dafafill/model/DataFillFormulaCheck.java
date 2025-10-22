/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.dafafill.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.dafafill.model.QueryField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;

@ApiModel(value="DataFillFormulaCheck", description="\u516c\u5f0f\u6821\u9a8c")
@JsonIgnoreProperties(ignoreUnknown=true)
public class DataFillFormulaCheck
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="\u516c\u5f0f", name="formula", required=true)
    private String formula;
    @ApiModelProperty(value="\u5df2\u9009\u5b57\u6bb5", name="queryFields", required=true)
    private List<QueryField> queryFields;

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public List<QueryField> getQueryFields() {
        return this.queryFields;
    }

    public void setQueryFields(List<QueryField> queryFields) {
        this.queryFields = queryFields;
    }
}

