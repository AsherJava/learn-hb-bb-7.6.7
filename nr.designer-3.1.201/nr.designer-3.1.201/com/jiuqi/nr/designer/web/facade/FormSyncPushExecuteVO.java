/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.facade;

import com.jiuqi.nr.designer.web.facade.FormCopySchemeVO;
import com.jiuqi.nr.designer.web.facade.FormSyncPushVO;
import java.util.ArrayList;
import java.util.List;

public class FormSyncPushExecuteVO {
    String srcFormSchemeKey;
    List<FormSyncPushVO> formSyncPushVOs;
    private List<FormCopySchemeVO> printSchemes = new ArrayList<FormCopySchemeVO>();
    private List<FormCopySchemeVO> formulaSchemes = new ArrayList<FormCopySchemeVO>();
    private List<FormCopySchemeVO> fiFormulaSchemes = new ArrayList<FormCopySchemeVO>();

    public String getSrcFormSchemeKey() {
        return this.srcFormSchemeKey;
    }

    public void setSrcFormSchemeKey(String srcFormSchemeKey) {
        this.srcFormSchemeKey = srcFormSchemeKey;
    }

    public List<FormSyncPushVO> getFormSyncPushVOs() {
        return this.formSyncPushVOs;
    }

    public void setFormSyncPushVOs(List<FormSyncPushVO> formSyncPushVOs) {
        this.formSyncPushVOs = formSyncPushVOs;
    }

    public List<FormCopySchemeVO> getPrintSchemes() {
        return this.printSchemes;
    }

    public void setPrintSchemes(List<FormCopySchemeVO> printSchemes) {
        this.printSchemes = printSchemes;
    }

    public List<FormCopySchemeVO> getFormulaSchemes() {
        return this.formulaSchemes;
    }

    public void setFormulaSchemes(List<FormCopySchemeVO> formulaSchemes) {
        this.formulaSchemes = formulaSchemes;
    }

    public List<FormCopySchemeVO> getFiFormulaSchemes() {
        return this.fiFormulaSchemes;
    }

    public void setFiFormulaSchemes(List<FormCopySchemeVO> fiFormulaSchemes) {
        this.fiFormulaSchemes = fiFormulaSchemes;
    }
}

