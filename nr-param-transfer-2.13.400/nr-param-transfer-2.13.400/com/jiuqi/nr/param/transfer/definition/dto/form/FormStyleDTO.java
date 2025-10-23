/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package com.jiuqi.nr.param.transfer.definition.dto.form;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nvwa.grid2.Grid2Data;

@JsonIgnoreProperties(ignoreUnknown=true)
public class FormStyleDTO {
    private String formKey;
    private int languageType;
    private String code;
    private String jsonData;
    private Grid2Data grid2Data;

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public int getLanguageType() {
        return this.languageType;
    }

    public void setLanguageType(int languageType) {
        this.languageType = languageType;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getJsonData() {
        return this.jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public Grid2Data getGrid2Data() {
        return this.grid2Data;
    }

    public void setGrid2Data(Grid2Data grid2Data) {
        this.grid2Data = grid2Data;
    }
}

