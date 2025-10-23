/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 */
package com.jiuqi.nr.param.transfer;

import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.param.transfer.definition.dto.formula.FormulaDTO;

public class ChangeObj {
    private String key;
    private String code;
    private String title;
    private String changeType;
    private String schemeKey;
    private String groupKey;

    public ChangeObj() {
    }

    public ChangeObj(String key, String code, String title, String changeType, String schemeKey, String groupKey) {
        this.key = key;
        this.code = code;
        this.title = title;
        this.changeType = changeType;
        this.schemeKey = schemeKey;
        this.groupKey = groupKey;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getChangeType() {
        return this.changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public static ChangeObj getChangeObj(FormulaDTO formula, String changeType) {
        ChangeObj result = new ChangeObj();
        result.setKey(formula.getKey());
        result.setCode(formula.getCode());
        result.setTitle(formula.getTitle());
        result.setSchemeKey(formula.getFormulaSchemeKey());
        result.setGroupKey(formula.getFormKey());
        result.setChangeType(changeType);
        return result;
    }

    public static ChangeObj getChangeObj(FormulaDefine formula, String changeType) {
        ChangeObj result = new ChangeObj();
        result.setKey(formula.getKey());
        result.setCode(formula.getCode());
        result.setTitle(formula.getTitle());
        result.setSchemeKey(formula.getFormulaSchemeKey());
        result.setGroupKey(formula.getFormKey());
        result.setChangeType(changeType);
        return result;
    }

    public static ChangeObj getChangeObj(DesignDataField dataField, String changeType) {
        ChangeObj result = new ChangeObj();
        result.setKey(dataField.getKey());
        result.setCode(dataField.getCode());
        result.setTitle(dataField.getTitle());
        result.setSchemeKey(dataField.getDataSchemeKey());
        result.setGroupKey(dataField.getDataTableKey());
        result.setChangeType(changeType);
        return result;
    }

    public static ChangeObj getChangeObj(FormDefine form, String changeType) {
        ChangeObj result = new ChangeObj();
        result.setKey(form.getKey());
        result.setCode(form.getFormCode());
        result.setTitle(form.getTitle());
        result.setSchemeKey(form.getFormScheme());
        result.setChangeType(changeType);
        return result;
    }
}

