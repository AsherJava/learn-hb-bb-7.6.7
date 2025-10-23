/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.FormulaSyntaxStyle
 */
package com.jiuqi.nr.task.form.dto;

import com.jiuqi.nr.definition.common.FormulaSyntaxStyle;
import java.util.List;

public class FormImportDTO {
    private String taskKey;
    private String formSchemeKey;
    private String formGroupKey;
    private List<String> fileKeys;
    private boolean generateField = false;
    private Integer formulaSyntaxStyle = FormulaSyntaxStyle.FORMULA_SYNTAX_STYLE_TRADITION.getValue();
    private Boolean saveImportData;

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getFormGroupKey() {
        return this.formGroupKey;
    }

    public void setFormGroupKey(String formGroupKey) {
        this.formGroupKey = formGroupKey;
    }

    public List<String> getFileKeys() {
        return this.fileKeys;
    }

    public void setFileKeys(List<String> fileKeys) {
        this.fileKeys = fileKeys;
    }

    public boolean isGenerateField() {
        return this.generateField;
    }

    public void setGenerateField(boolean generateField) {
        this.generateField = generateField;
    }

    public Boolean isSaveImportData() {
        return this.saveImportData;
    }

    public void setSaveImportData(Boolean saveImportData) {
        this.saveImportData = saveImportData;
    }

    public Integer getFormulaSyntaxStyle() {
        return this.formulaSyntaxStyle;
    }

    public void setFormulaSyntaxStyle(Integer formulaSyntaxStyle) {
        this.formulaSyntaxStyle = formulaSyntaxStyle;
    }
}

