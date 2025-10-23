/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.dto;

import com.jiuqi.nr.task.form.dto.FormCopyAttSchemeVO;
import java.util.List;

public class FormSyncVO {
    private String desFormKey;
    private String desFormTitle;
    private String desFormCode;
    private String desFormOrder;
    private String srcFormKey;
    private String srcFormTitle;
    private String srcFormCode;
    private String srcTaskKey;
    private String srcTaskTitle;
    private String srcTaskCode;
    private String srcFormSchemeKey;
    private String srcFormSchemeTitle;
    private String srcFormSchemeCode;
    private List<FormCopyAttSchemeVO> printSchemes;
    private List<FormCopyAttSchemeVO> formulaSchemes;

    public String getDesFormKey() {
        return this.desFormKey;
    }

    public void setDesFormKey(String desFormKey) {
        this.desFormKey = desFormKey;
    }

    public String getDesFormTitle() {
        return this.desFormTitle;
    }

    public void setDesFormTitle(String desFormTitle) {
        this.desFormTitle = desFormTitle;
    }

    public String getDesFormCode() {
        return this.desFormCode;
    }

    public void setDesFormCode(String desFormCode) {
        this.desFormCode = desFormCode;
    }

    public String getDesFormOrder() {
        return this.desFormOrder;
    }

    public void setDesFormOrder(String desFormOrder) {
        this.desFormOrder = desFormOrder;
    }

    public String getSrcFormKey() {
        return this.srcFormKey;
    }

    public void setSrcFormKey(String srcFormKey) {
        this.srcFormKey = srcFormKey;
    }

    public String getSrcFormTitle() {
        return this.srcFormTitle;
    }

    public void setSrcFormTitle(String srcFormTitle) {
        this.srcFormTitle = srcFormTitle;
    }

    public String getSrcFormCode() {
        return this.srcFormCode;
    }

    public void setSrcFormCode(String srcFormCode) {
        this.srcFormCode = srcFormCode;
    }

    public String getSrcTaskKey() {
        return this.srcTaskKey;
    }

    public void setSrcTaskKey(String srcTaskKey) {
        this.srcTaskKey = srcTaskKey;
    }

    public String getSrcTaskTitle() {
        return this.srcTaskTitle;
    }

    public void setSrcTaskTitle(String srcTaskTitle) {
        this.srcTaskTitle = srcTaskTitle;
    }

    public String getSrcFormSchemeKey() {
        return this.srcFormSchemeKey;
    }

    public void setSrcFormSchemeKey(String srcFormSchemeKey) {
        this.srcFormSchemeKey = srcFormSchemeKey;
    }

    public String getSrcFormSchemeTitle() {
        return this.srcFormSchemeTitle;
    }

    public void setSrcFormSchemeTitle(String srcFormSchemeTitle) {
        this.srcFormSchemeTitle = srcFormSchemeTitle;
    }

    public String getSrcFormSchemeCode() {
        return this.srcFormSchemeCode;
    }

    public void setSrcFormSchemeCode(String srcFormSchemeCode) {
        this.srcFormSchemeCode = srcFormSchemeCode;
    }

    public List<FormCopyAttSchemeVO> getPrintSchemes() {
        return this.printSchemes;
    }

    public void setPrintSchemes(List<FormCopyAttSchemeVO> printSchemes) {
        this.printSchemes = printSchemes;
    }

    public List<FormCopyAttSchemeVO> getFormulaSchemes() {
        return this.formulaSchemes;
    }

    public void setFormulaSchemes(List<FormCopyAttSchemeVO> formulaSchemes) {
        this.formulaSchemes = formulaSchemes;
    }

    public String getSrcTaskCode() {
        return this.srcTaskCode;
    }

    public void setSrcTaskCode(String srcTaskCode) {
        this.srcTaskCode = srcTaskCode;
    }
}

