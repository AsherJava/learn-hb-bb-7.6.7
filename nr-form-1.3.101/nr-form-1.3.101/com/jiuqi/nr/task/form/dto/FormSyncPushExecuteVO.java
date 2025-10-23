/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.dto;

import com.jiuqi.nr.task.form.dto.FormCopySchemeVO;
import com.jiuqi.nr.task.form.dto.FormSyncPushVO;
import java.util.ArrayList;
import java.util.List;

public class FormSyncPushExecuteVO {
    String srcFormSchemeKey;
    List<FormSyncPushVO> formSyncPushVOs;
    private List<FormCopySchemeVO> printSchemes = new ArrayList<FormCopySchemeVO>();
    private List<FormCopySchemeVO> formulaSchemes = new ArrayList<FormCopySchemeVO>();

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
}

