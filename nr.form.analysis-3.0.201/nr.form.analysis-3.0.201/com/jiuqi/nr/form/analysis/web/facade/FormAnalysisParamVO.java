/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.form.analysis.web.facade;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.form.analysis.web.facade.CommonDimParamVO;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class FormAnalysisParamVO
implements Serializable {
    private static final long serialVersionUID = -6343743956713976025L;
    private String schemeKey;
    private String formulaSchemeKey;
    private String formKey;
    private List<String> selectedFormKeys;
    private FormSelectionEnum formSelectionEnum;
    private boolean allEntity;
    private Map<String, DimensionValue> destDims;
    private List<CommonDimParamVO> srcDimension;

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
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

    public List<String> getSelectedFormKeys() {
        return this.selectedFormKeys;
    }

    public void setSelectedFormKeys(List<String> selectedFormKeys) {
        this.selectedFormKeys = selectedFormKeys;
    }

    public FormSelectionEnum getFormSelectionEnum() {
        return this.formSelectionEnum;
    }

    public void setFormSelectionEnum(FormSelectionEnum formSelectionEnum) {
        this.formSelectionEnum = formSelectionEnum;
    }

    public boolean isAllEntity() {
        return this.allEntity;
    }

    public void setAllEntity(boolean allEntity) {
        this.allEntity = allEntity;
    }

    public Map<String, DimensionValue> getDestDims() {
        return this.destDims;
    }

    public void setDestDims(Map<String, DimensionValue> destDims) {
        this.destDims = destDims;
    }

    public List<CommonDimParamVO> getSrcDimension() {
        return this.srcDimension;
    }

    public void setSrcDimension(List<CommonDimParamVO> srcDimension) {
        this.srcDimension = srcDimension;
    }

    public static enum FormSelectionEnum {
        ALL,
        CURRENT,
        SELECTION;

    }
}

