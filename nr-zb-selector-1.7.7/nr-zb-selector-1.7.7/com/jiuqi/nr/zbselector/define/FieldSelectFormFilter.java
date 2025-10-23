/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.FormType
 */
package com.jiuqi.nr.zbselector.define;

import com.jiuqi.nr.definition.common.FormType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FieldSelectFormFilter {
    private boolean canFormulaField;
    private String formGroupKey;
    private String schemeKey;
    private boolean displayFMDM = true;
    private boolean onlyDisplayFMDM;
    private boolean useFormCondition;
    private Map<String, String[]> formConditions = new HashMap<String, String[]>();
    private Map<String, String> filterCondition = new HashMap<String, String>();
    private String filterName = "defalutFieldSelectFormFilterImpl";
    private List<FormType> filterFormTypes = new ArrayList<FormType>();
    private String formKey;
    private boolean useRelateFieldType;
    private Boolean showHiddenZb;

    public boolean isCanFormulaField() {
        return this.canFormulaField;
    }

    public void setCanFormulaField(boolean canFormulaField) {
        this.canFormulaField = canFormulaField;
    }

    public String getFormGroupKey() {
        return this.formGroupKey;
    }

    public void setFormGroupKey(String formGroupKey) {
        this.formGroupKey = formGroupKey;
    }

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    public boolean isDisplayFMDM() {
        return this.displayFMDM;
    }

    public void setDisplayFMDM(boolean displayFMDM) {
        this.displayFMDM = displayFMDM;
    }

    public boolean isOnlyDisplayFMDM() {
        return this.onlyDisplayFMDM;
    }

    public void setOnlyDisplayFMDM(boolean onlyDisplayFMDM) {
        this.onlyDisplayFMDM = onlyDisplayFMDM;
    }

    public boolean isUseFormCondition() {
        return this.useFormCondition;
    }

    public void setUseFormCondition(boolean useFormCondition) {
        this.useFormCondition = useFormCondition;
    }

    public Map<String, String[]> getFormConditions() {
        return this.formConditions;
    }

    public void setFormConditions(Map<String, String[]> formConditions) {
        this.formConditions = formConditions;
    }

    public Map<String, String> getFilterCondition() {
        return this.filterCondition;
    }

    public void setFilterCondition(Map<String, String> filterCondition) {
        this.filterCondition = filterCondition;
    }

    public String getFilterName() {
        return this.filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public List<FormType> getFilterFormTypes() {
        return this.filterFormTypes;
    }

    public void setFilterFormTypes(List<FormType> filterFormTypes) {
        this.filterFormTypes = filterFormTypes;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public boolean isUseRelateFieldType() {
        return this.useRelateFieldType;
    }

    public void setUseRelateFieldType(boolean useRelateFieldType) {
        this.useRelateFieldType = useRelateFieldType;
    }

    public Boolean getShowHiddenZb() {
        return this.showHiddenZb;
    }

    public void setShowHiddenZb(Boolean showHiddenZb) {
        this.showHiddenZb = showHiddenZb;
    }
}

