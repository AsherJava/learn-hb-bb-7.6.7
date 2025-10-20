/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 */
package com.jiuqi.nr.definition.formulamapping.facade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.definition.formulamapping.bean.FormulaMappingDefine;
import com.jiuqi.nr.definition.formulamapping.facade.FormulaObj;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class FormulaMappingObj
extends FormulaObj {
    private String key;
    private String sourceId;
    private String sourceCode;
    private String sourceExpression;
    private int sourceCheckType;
    private int mode;
    private double order;
    private List<FormulaMappingObj> children;

    public String getSourceCode() {
        return this.sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getSourceExpression() {
        return this.sourceExpression;
    }

    public void setSourceExpression(String sourceExpression) {
        this.sourceExpression = sourceExpression;
    }

    public int getSourceCheckType() {
        return this.sourceCheckType;
    }

    public void setSourceCheckType(int sourceCheckType) {
        this.sourceCheckType = sourceCheckType;
    }

    public int getMode() {
        return this.mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public double getOrder() {
        return this.order;
    }

    public void setOrder(double order) {
        this.order = order;
    }

    public List<FormulaMappingObj> getChildren() {
        return this.children;
    }

    public void setChildren(List<FormulaMappingObj> children) {
        this.children = children;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSourceId() {
        return this.sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public static FormulaMappingObj toFormulaMappingObj(FormulaMappingDefine obj) {
        FormulaMappingObj result = new FormulaMappingObj();
        result.setKey(obj.getKey());
        result.setFormKey(obj.getTargetFormKey());
        result.setId(obj.getTargetKey());
        result.setCode(obj.getTargetCode());
        result.setExpression(obj.getTargetExpression());
        result.setCheckType(obj.getTargetCheckType());
        result.setSourceId(obj.getSourceKey());
        result.setSourceCode(obj.getSourceCode());
        result.setSourceExpression(obj.getSourceExpression());
        result.setSourceCheckType(obj.getSourceCheckType());
        result.setMode(obj.getMode());
        result.setKind(obj.getKind());
        result.setGroup(obj.getGroup());
        result.setOrder(obj.getOrder());
        return result;
    }

    public static FormulaMappingDefine toFormulaMappingDefine(FormulaMappingObj obj, String schemeKey) {
        FormulaMappingDefine result = FormulaMappingObj.toDefineWithoutSource(obj, schemeKey);
        result.setSourceKey(obj.getSourceId());
        result.setSourceCode(obj.getSourceCode());
        result.setSourceExpression(obj.getSourceExpression());
        result.setSourceCheckType(obj.getSourceCheckType());
        return result;
    }

    public static FormulaMappingDefine toDefineWithoutSource(FormulaMappingObj obj, String schemeKey) {
        FormulaMappingDefine result = new FormulaMappingDefine();
        result.setKey(obj.getKey());
        result.setSchemeKey(schemeKey);
        result.setTargetFormKey(obj.getFormKey());
        result.setTargetKey(obj.getId());
        result.setTargetCode(obj.getCode());
        result.setTargetExpression(obj.getExpression());
        result.setTargetCheckType(obj.getCheckType());
        result.setMode(obj.getMode());
        result.setKind(obj.getKind());
        result.setGroup(obj.getGroup());
        result.setOrder(obj.getOrder());
        return result;
    }
}

