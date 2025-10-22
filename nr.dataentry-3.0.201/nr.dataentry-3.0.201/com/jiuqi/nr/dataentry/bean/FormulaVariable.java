/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormulaVariDefine
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.definition.facade.FormulaVariDefine;

public class FormulaVariable {
    private String id;
    private String code;
    private String title;
    private int type;
    private int valueType;
    private int initType;
    private String initValue;
    private int length;
    private String formSchemeKey;

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

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getValueType() {
        return this.valueType;
    }

    public void setValueType(int valueType) {
        this.valueType = valueType;
    }

    public int getInitType() {
        return this.initType;
    }

    public void setInitType(int initType) {
        this.initType = initType;
    }

    public String getInitValue() {
        return this.initValue;
    }

    public void setInitValue(String initValue) {
        this.initValue = initValue;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public FormulaVariable convertToFormulaVariable(FormulaVariDefine designFormulaVariableDefine) {
        this.setId(designFormulaVariableDefine.getKey());
        this.setCode(designFormulaVariableDefine.getCode());
        this.setFormSchemeKey(designFormulaVariableDefine.getFormSchemeKey());
        this.setInitType(designFormulaVariableDefine.getInitType());
        this.setLength(designFormulaVariableDefine.getLength());
        this.setTitle(designFormulaVariableDefine.getTitle());
        this.setType(designFormulaVariableDefine.getType());
        this.setValueType(designFormulaVariableDefine.getValueType());
        this.setInitValue(designFormulaVariableDefine.getInitValue());
        return this;
    }
}

