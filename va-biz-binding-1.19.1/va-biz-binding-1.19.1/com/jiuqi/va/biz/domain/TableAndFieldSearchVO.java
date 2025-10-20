/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.va.biz.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.UUID;

public class TableAndFieldSearchVO {
    private UUID id;
    private String name;
    private String title;
    private String formulaText;
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private String fieldType;

    public TableAndFieldSearchVO() {
    }

    public TableAndFieldSearchVO(UUID id, String name, String title, String formulaText) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.formulaText = formulaText;
    }

    public TableAndFieldSearchVO(UUID id, String name, String title, String formulaText, String fieldType) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.formulaText = formulaText;
        this.fieldType = fieldType;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFormulaText() {
        return this.formulaText;
    }

    public void setFormulaText(String formulaText) {
        this.formulaText = formulaText;
    }

    public String getFieldType() {
        return this.fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }
}

