/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package com.jiuqi.nr.designer.web.rest.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.designer.common.NrGrid2DataDeserialize;
import com.jiuqi.nr.designer.common.NrGrid2DataSerialize;
import com.jiuqi.nr.designer.web.rest.vo.ReverseRegionVO;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.util.Map;

public class ReverseFormVO {
    private String dataSchemeKey;
    private String dataSchemePrefix;
    private String formSchemeKey;
    private String formSchemeTitle;
    private String formGroupTitle;
    private String formKey;
    private String formCode;
    private String formTitle;
    private int formType;
    private Map<String, ReverseRegionVO> regions;
    @JsonSerialize(using=NrGrid2DataSerialize.class)
    @JsonDeserialize(using=NrGrid2DataDeserialize.class)
    private Grid2Data formStyle;
    private int language;

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public String getDataSchemePrefix() {
        return this.dataSchemePrefix;
    }

    public void setDataSchemePrefix(String dataSchemePrefix) {
        this.dataSchemePrefix = dataSchemePrefix;
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

    public String getFormGroupTitle() {
        return this.formGroupTitle;
    }

    public void setFormGroupTitle(String formGroupTitle) {
        this.formGroupTitle = formGroupTitle;
    }

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getFormTitle() {
        return this.formTitle;
    }

    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle;
    }

    public int getFormType() {
        return this.formType;
    }

    public void setFormType(int formType) {
        this.formType = formType;
    }

    public Map<String, ReverseRegionVO> getRegions() {
        return this.regions;
    }

    public void setRegions(Map<String, ReverseRegionVO> regions) {
        this.regions = regions;
    }

    public Grid2Data getFormStyle() {
        return this.formStyle;
    }

    public void setFormStyle(Grid2Data formStyle) {
        this.formStyle = formStyle;
    }

    public int getLanguage() {
        return this.language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }
}

