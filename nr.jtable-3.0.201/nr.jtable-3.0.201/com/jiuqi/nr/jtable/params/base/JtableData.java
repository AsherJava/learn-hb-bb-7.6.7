/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.nr.jtable.params.base.FormAction;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.base.FormulaSchemeData;
import com.jiuqi.nr.jtable.params.base.IFormStructureData;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class JtableData {
    @ApiModelProperty(value="\u62a5\u8868\u5c5e\u6027", name="form")
    private FormData form;
    @ApiModelProperty(value="\u62a5\u8868\u7ed3\u6784", name="structure")
    private IFormStructureData structure;
    @ApiModelProperty(value="\u62a5\u8868\u52a8\u4f5c", name="actions")
    private List<FormAction> actions = new ArrayList<FormAction>();
    @ApiModelProperty(value="\u62a5\u8868\u65f6\u95f4\u6233", name="dataCheckTag")
    private String dataCheckTag;
    @ApiModelProperty(value="\u524d\u7aef\u811a\u672c\u4e2d\u8de8\u8868\u516c\u5f0f", name="formulaValues")
    private Map<String, String> formulaValues;
    @ApiModelProperty(value="\u8d22\u52a1\u63d0\u53d6\u65b9\u6848", name="soluction")
    private FormulaSchemeData formulaSchemeData;

    public FormData getForm() {
        return this.form;
    }

    public void setForm(FormData form) {
        this.form = form;
    }

    public IFormStructureData getStructure() {
        return this.structure;
    }

    public void setStructure(IFormStructureData structure) {
        this.structure = structure;
    }

    public List<FormAction> getActions() {
        return this.actions;
    }

    public void setActions(List<FormAction> actions) {
        this.actions = actions;
    }

    public String getFormKey() {
        return this.form != null ? this.form.getKey() : "";
    }

    public String getDataCheckTag() {
        return this.dataCheckTag;
    }

    public void setDataCheckTag(String dataCheckTag) {
        this.dataCheckTag = dataCheckTag;
    }

    public Map<String, String> getFormulaValues() {
        return this.formulaValues;
    }

    public void setFormulaValues(Map<String, String> formulaValues) {
        this.formulaValues = formulaValues;
    }

    public FormulaSchemeData getFormulaSchemeData() {
        return this.formulaSchemeData;
    }

    public void setFormulaSchemeData(FormulaSchemeData formulaSchemeData) {
        this.formulaSchemeData = formulaSchemeData;
    }
}

