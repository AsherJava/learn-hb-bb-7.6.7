/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.infc.INRContext
 *  com.jiuqi.nr.data.logic.internal.util.CheckResultUtil
 */
package com.jiuqi.nr.jtable.params.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.infc.INRContext;
import com.jiuqi.nr.data.logic.internal.util.CheckResultUtil;
import com.jiuqi.nr.jtable.annotation.JtableLog;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.output.DescriptionInfo;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class FormulaCheckDesInfo
extends JtableLog
implements INRContext {
    private static final long serialVersionUID = 1L;
    private String desKey;
    private String formSchemeKey;
    private String formulaSchemeKey;
    private String formKey;
    private String formulaKey;
    private String formulaCode;
    private int globRow;
    private int globCol;
    private String floatId;
    private Map<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
    private DescriptionInfo descriptionInfo = new DescriptionInfo();
    private String taskKey;
    private String sourceFormSchemeKey;
    private Map<String, DimensionValue> sourceDimensionSet = new HashMap<String, DimensionValue>();
    private String commitError;
    private String checkType;
    private boolean editCheckDesEnable = true;
    private String contextEntityId;
    private String contextFilterExpression;

    public boolean isEditCheckDesEnable() {
        return this.editCheckDesEnable;
    }

    public void setEditCheckDesEnable(boolean editCheckDesEnable) {
        this.editCheckDesEnable = editCheckDesEnable;
    }

    public String getCheckType() {
        return this.checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getCommitError() {
        return this.commitError;
    }

    public void setCommitError(String commitError) {
        this.commitError = commitError;
    }

    public String getSourceFormSchemeKey() {
        return this.sourceFormSchemeKey;
    }

    public void setSourceFormSchemeKey(String sourceFormSchemeKey) {
        this.sourceFormSchemeKey = sourceFormSchemeKey;
    }

    public String getDesKey() {
        if (this.desKey == null) {
            this.desKey = CheckResultUtil.buildRECID((String)this.formulaSchemeKey, (String)this.formKey, (String)this.formulaKey.substring(0, 36), (int)this.globRow, (int)this.globCol, this.dimensionSet);
        }
        return this.desKey;
    }

    public void setDesKey(String desKey) {
        this.desKey = desKey;
    }

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

    public String getFormulaCode() {
        return this.formulaCode;
    }

    public void setFormulaCode(String formulaCode) {
        this.formulaCode = formulaCode;
    }

    public String getFormulaKey() {
        return this.formulaKey;
    }

    public void setFormulaKey(String formulaKey) {
        this.formulaKey = formulaKey;
    }

    public int getGlobRow() {
        return this.globRow;
    }

    public void setGlobRow(int globRow) {
        this.globRow = globRow;
    }

    public int getGlobCol() {
        return this.globCol;
    }

    public void setGlobCol(int globCol) {
        this.globCol = globCol;
    }

    public String getFloatId() {
        return this.floatId;
    }

    public void setFloatId(String floatId) {
        this.floatId = floatId;
    }

    public Map<String, DimensionValue> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, DimensionValue> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public DescriptionInfo getDescriptionInfo() {
        return this.descriptionInfo;
    }

    public void setDescriptionInfo(DescriptionInfo descriptionInfo) {
        this.descriptionInfo = descriptionInfo;
    }

    @Override
    public JtableContext getContext() {
        JtableContext jtableContext = new JtableContext();
        jtableContext.setDimensionSet(this.dimensionSet);
        jtableContext.setFormKey(this.formKey);
        jtableContext.setFormSchemeKey(this.formSchemeKey);
        jtableContext.setFormulaSchemeKey(this.formulaSchemeKey);
        jtableContext.setTaskKey(this.taskKey);
        return jtableContext;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public Map<String, DimensionValue> getSourceDimensionSet() {
        return this.sourceDimensionSet;
    }

    public void setSourceDimensionSet(Map<String, DimensionValue> sourceDimensionSet) {
        this.sourceDimensionSet = sourceDimensionSet;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextEntityId;
    }
}

