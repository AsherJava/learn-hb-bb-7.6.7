/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.designer.web.facade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ValidationObj {
    @JsonProperty(value="ID")
    private String id;
    @JsonProperty(value="FormulaID")
    private String formulaId;
    @JsonProperty(value="ValidationType")
    private int validationType;
    @JsonProperty(value="DataValidationType")
    private int dataValidationType;
    @JsonProperty(value="NumberOne")
    private String numberOne;
    @JsonProperty(value="NumberTwo")
    private String numberTwo;
    @JsonProperty(value="EffectedLink")
    private String effectedLink;
    @JsonProperty(value="IsNew")
    private boolean isNew = false;
    @JsonProperty(value="IsDeleted")
    private boolean isDeleted = false;
    @JsonProperty(value="IsDirty")
    private boolean isDirty = false;
    @JsonProperty(value="DataValidationMap")
    private Map<String, String> dataValidationMap;

    public Map<String, String> getDataValidationMap() {
        return this.dataValidationMap;
    }

    public void setDataValidationMap(Map<String, String> dataValidationMap) {
        this.dataValidationMap = dataValidationMap;
    }

    @JsonIgnore
    public String getId() {
        return this.id;
    }

    @JsonIgnore
    public void setId(String id) {
        this.id = id;
    }

    @JsonIgnore
    public String getFormulaId() {
        return this.formulaId;
    }

    @JsonIgnore
    public void setFormulaId(String formulaId) {
        this.formulaId = formulaId;
    }

    @JsonIgnore
    public int getValidationType() {
        return this.validationType;
    }

    @JsonIgnore
    public void setValidationType(int validationType) {
        this.validationType = validationType;
    }

    @JsonIgnore
    public int getDataValidationType() {
        return this.dataValidationType;
    }

    @JsonIgnore
    public void setDataValidationType(int dataValidationType) {
        this.dataValidationType = dataValidationType;
    }

    @JsonIgnore
    public String getNumberOne() {
        return this.numberOne;
    }

    @JsonIgnore
    public void setNumberOne(String numberOne) {
        this.numberOne = numberOne;
    }

    @JsonIgnore
    public String getNumberTwo() {
        return this.numberTwo;
    }

    @JsonIgnore
    public void setNumberTwo(String numberTwo) {
        this.numberTwo = numberTwo;
    }

    @JsonIgnore
    public String getEffectedLink() {
        return this.effectedLink;
    }

    @JsonIgnore
    public void setEffectedLink(String effectedLink) {
        this.effectedLink = effectedLink;
    }

    @JsonIgnore
    public boolean isNew() {
        return this.isNew;
    }

    @JsonIgnore
    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    @JsonIgnore
    public boolean isDeleted() {
        return this.isDeleted;
    }

    @JsonIgnore
    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @JsonIgnore
    public boolean isDirty() {
        return this.isDirty;
    }

    @JsonIgnore
    public void setDirty(boolean isDirty) {
        this.isDirty = isDirty;
    }
}

