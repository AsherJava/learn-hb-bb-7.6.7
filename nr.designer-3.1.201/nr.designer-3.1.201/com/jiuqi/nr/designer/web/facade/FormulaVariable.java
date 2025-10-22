/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormulaVariDefine
 */
package com.jiuqi.nr.designer.web.facade;

import com.jiuqi.nr.definition.facade.FormulaVariDefine;

public class FormulaVariable {
    private String id;
    private String code;
    private String title;
    private int type;
    private int valueType;
    private String order;
    private int length;
    private int initType;
    private String initValue;
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

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
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

    public FormulaVariDefine convertToDefine(FormulaVariDefine designFormulaVariableDefine) {
        designFormulaVariableDefine.setCode(this.getCode());
        designFormulaVariableDefine.setFormSchemeKey(this.getFormSchemeKey());
        designFormulaVariableDefine.setInitType(this.getInitType());
        designFormulaVariableDefine.setKey(this.getId());
        designFormulaVariableDefine.setLength(this.getLength());
        designFormulaVariableDefine.setTitle(this.getTitle());
        designFormulaVariableDefine.setOrder(this.getOrder());
        designFormulaVariableDefine.setType(this.getType());
        designFormulaVariableDefine.setValueType(this.getValueType());
        designFormulaVariableDefine.setInitValue(this.getInitValue());
        return designFormulaVariableDefine;
    }

    public FormulaVariable convertToFormulaVariable(FormulaVariDefine designFormulaVariableDefine) {
        this.setCode(designFormulaVariableDefine.getCode());
        this.setFormSchemeKey(designFormulaVariableDefine.getFormSchemeKey());
        this.setInitType(designFormulaVariableDefine.getInitType());
        this.setId(designFormulaVariableDefine.getKey());
        this.setLength(designFormulaVariableDefine.getLength());
        this.setTitle(designFormulaVariableDefine.getTitle());
        this.setOrder(designFormulaVariableDefine.getOrder());
        this.setType(designFormulaVariableDefine.getType());
        this.setValueType(designFormulaVariableDefine.getValueType());
        this.setInitValue(designFormulaVariableDefine.getInitValue());
        return this;
    }
}

