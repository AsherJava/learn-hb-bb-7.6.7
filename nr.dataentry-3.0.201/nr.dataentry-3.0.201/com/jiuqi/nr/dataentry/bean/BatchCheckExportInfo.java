/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.context.infc.impl.NRContext
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.context.infc.impl.NRContext;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown=true)
public class BatchCheckExportInfo
extends NRContext {
    private JtableContext context;
    private String formKey;
    private Map<String, List<String>> formulas = new HashMap<String, List<String>>();
    private String orderField;
    private String exportType;
    private String formulaSchemeKeys;
    private String asyncTaskKey;
    private List<Integer> exportCheckTypes;
    private List<Integer> uploadCheckTypes = new ArrayList<Integer>();
    private boolean checkDesNull = true;
    private boolean checkDesPass = true;
    private boolean checkDesMustPass = true;
    private Map<Integer, Integer> checkDesCheckTypes;
    private boolean effectUpload = false;
    private boolean filterExportResult = true;
    private boolean searchByFormula;
    private String descriptionFilterType;
    private String descriptionFilterContent;

    public String getDescriptionFilterType() {
        return this.descriptionFilterType;
    }

    public void setDescriptionFilterType(String descriptionFilterType) {
        this.descriptionFilterType = descriptionFilterType;
    }

    public String getDescriptionFilterContent() {
        return this.descriptionFilterContent;
    }

    public void setDescriptionFilterContent(String descriptionFilterContent) {
        this.descriptionFilterContent = descriptionFilterContent;
    }

    public boolean isSearchByFormula() {
        return this.searchByFormula;
    }

    public void setSearchByFormula(boolean searchByFormula) {
        this.searchByFormula = searchByFormula;
    }

    public boolean isFilterExportResult() {
        return this.filterExportResult;
    }

    public void setFilterExportResult(boolean filterExportResult) {
        this.filterExportResult = filterExportResult;
    }

    public List<Integer> getUploadCheckTypes() {
        return this.uploadCheckTypes;
    }

    public void setUploadCheckTypes(List<Integer> uploadCheckTypes) {
        this.uploadCheckTypes = uploadCheckTypes;
    }

    public boolean isCheckDesNull() {
        return this.checkDesNull;
    }

    public void setCheckDesNull(boolean checkDesNull) {
        this.checkDesNull = checkDesNull;
    }

    public boolean isCheckDesPass() {
        return this.checkDesPass;
    }

    public void setCheckDesPass(boolean checkDesPass) {
        this.checkDesPass = checkDesPass;
    }

    public boolean isCheckDesMustPass() {
        return this.checkDesMustPass;
    }

    public void setCheckDesMustPass(boolean checkDesMustPass) {
        this.checkDesMustPass = checkDesMustPass;
    }

    public Map<Integer, Integer> getCheckDesCheckTypes() {
        return this.checkDesCheckTypes;
    }

    public void setCheckDesCheckTypes(Map<Integer, Integer> checkDesCheckTypes) {
        this.checkDesCheckTypes = checkDesCheckTypes;
    }

    public boolean isEffectUpload() {
        return this.effectUpload;
    }

    public void setEffectUpload(boolean effectUpload) {
        this.effectUpload = effectUpload;
    }

    public List<Integer> getExportCheckTypes() {
        return this.exportCheckTypes;
    }

    public void setExportCheckTypes(List<Integer> exportCheckTypes) {
        this.exportCheckTypes = exportCheckTypes;
    }

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public Map<String, List<String>> getFormulas() {
        return this.formulas;
    }

    public void setFormulas(Map<String, List<String>> formulas) {
        this.formulas = formulas;
    }

    public String getOrderField() {
        return this.orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public String getExportType() {
        return this.exportType;
    }

    public void setExportType(String exportType) {
        this.exportType = exportType;
    }

    public String getFormulaSchemeKeys() {
        return this.formulaSchemeKeys;
    }

    public void setFormulaSchemeKeys(String formulaSchemeKeys) {
        this.formulaSchemeKeys = formulaSchemeKeys;
    }

    public String getAsyncTaskKey() {
        return this.asyncTaskKey;
    }

    public void setAsyncTaskKey(String asyncTaskKey) {
        this.asyncTaskKey = asyncTaskKey;
    }
}

