/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.mappingscheme.client.vo;

import java.io.Serializable;

public class DataSchemeOptionVO
implements Serializable {
    private static final long serialVersionUID = -6351320237880872836L;
    private String id;
    private String code;
    private String title;
    private String optionValue;
    private String optionName;
    private String paramType;
    private String showForm;
    private Boolean multipleFlag;
    private Boolean readonlyFlag;
    private String baseDataTable;
    private String source;
    private String description;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOptionValue() {
        return this.optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }

    public String getOptionName() {
        return this.optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParamType() {
        return this.paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public String getShowForm() {
        return this.showForm;
    }

    public void setShowForm(String showForm) {
        this.showForm = showForm;
    }

    public Boolean getMultipleFlag() {
        return this.multipleFlag;
    }

    public void setMultipleFlag(Boolean multipleFlag) {
        this.multipleFlag = multipleFlag;
    }

    public Boolean getReadonlyFlag() {
        return this.readonlyFlag;
    }

    public void setReadonlyFlag(Boolean readonlyFlag) {
        this.readonlyFlag = readonlyFlag;
    }

    public String getBaseDataTable() {
        return this.baseDataTable;
    }

    public void setBaseDataTable(String baseDataTable) {
        this.baseDataTable = baseDataTable;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

