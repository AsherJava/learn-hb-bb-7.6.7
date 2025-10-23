/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.controller.dto;

import com.jiuqi.nr.task.form.controller.dto.ReverseModeRegionDTO;
import com.jiuqi.nr.task.form.service.reversemodel.IReverseModelDataProvider;
import java.util.List;

public class ReverseModeParam {
    private String dataSchemeKey;
    private String dataSchemePrefix;
    private String dataSchemeTitle;
    private String formKey;
    private String formType;
    private String formTitle;
    private String formCode;
    private String formGroupKey;
    private String formGroupTitle;
    private String formSchemeKey;
    private String formSchemeTitle;
    private List<ReverseModeRegionDTO> regions;
    private List<String> tableCodes;
    private IReverseModelDataProvider reverseModelDataProvider;

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public List<ReverseModeRegionDTO> getRegions() {
        return this.regions;
    }

    public void setRegions(List<ReverseModeRegionDTO> regions) {
        this.regions = regions;
    }

    public List<String> getTableCodes() {
        return this.tableCodes;
    }

    public void setTableCodes(List<String> tableCodes) {
        this.tableCodes = tableCodes;
    }

    public String getDataSchemePrefix() {
        return this.dataSchemePrefix;
    }

    public void setDataSchemePrefix(String dataSchemePrefix) {
        this.dataSchemePrefix = dataSchemePrefix;
    }

    public String getDataSchemeTitle() {
        return this.dataSchemeTitle;
    }

    public void setDataSchemeTitle(String dataSchemeTitle) {
        this.dataSchemeTitle = dataSchemeTitle;
    }

    public String getFormTitle() {
        return this.formTitle;
    }

    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle;
    }

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getFormGroupKey() {
        return this.formGroupKey;
    }

    public void setFormGroupKey(String formGroupKey) {
        this.formGroupKey = formGroupKey;
    }

    public String getFormGroupTitle() {
        return this.formGroupTitle;
    }

    public void setFormGroupTitle(String formGroupTitle) {
        this.formGroupTitle = formGroupTitle;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getFormSchemeTitle() {
        return this.formSchemeTitle;
    }

    public void setFormSchemeTitle(String formSchemeTitle) {
        this.formSchemeTitle = formSchemeTitle;
    }

    public String getFormType() {
        return this.formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public IReverseModelDataProvider getReverseModelDataProvider() {
        return this.reverseModelDataProvider;
    }

    public void setReverseModelDataProvider(IReverseModelDataProvider reverseModelDataProvider) {
        this.reverseModelDataProvider = reverseModelDataProvider;
    }
}

