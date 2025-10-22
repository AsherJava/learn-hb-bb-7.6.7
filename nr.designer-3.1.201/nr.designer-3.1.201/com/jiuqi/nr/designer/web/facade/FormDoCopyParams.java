/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.facade;

import com.jiuqi.nr.designer.web.facade.FormCopyParamsVO;
import java.util.List;
import java.util.Map;

public class FormDoCopyParams {
    private String srcFormSchemeKey;
    private String desFormSchemeKey;
    private List<FormCopyParamsVO> copyParams;
    private boolean ifCopyGroup;
    private String desFormGroupKey;
    private Map<String, String> formulaAttMap;
    private Map<String, String> fiFormulaAttMap;
    private Map<String, String> printAttMap;

    public List<FormCopyParamsVO> getCopyParams() {
        return this.copyParams;
    }

    public void setCopyParams(List<FormCopyParamsVO> copyParams) {
        this.copyParams = copyParams;
    }

    public String getSrcFormSchemeKey() {
        return this.srcFormSchemeKey;
    }

    public void setSrcFormSchemeKey(String srcFormSchemeKey) {
        this.srcFormSchemeKey = srcFormSchemeKey;
    }

    public String getDesFormSchemeKey() {
        return this.desFormSchemeKey;
    }

    public void setDesFormSchemeKey(String desFormSchemeKey) {
        this.desFormSchemeKey = desFormSchemeKey;
    }

    public String getDesFormGroupKey() {
        return this.desFormGroupKey;
    }

    public void setDesFormGroupKey(String desFormGroupKey) {
        this.desFormGroupKey = desFormGroupKey;
    }

    public boolean isIfCopyGroup() {
        return this.ifCopyGroup;
    }

    public void setIfCopyGroup(boolean ifCopyGroup) {
        this.ifCopyGroup = ifCopyGroup;
    }

    public Map<String, String> getFormulaAttMap() {
        return this.formulaAttMap;
    }

    public void setFormulaAttMap(Map<String, String> formulaAttMap) {
        this.formulaAttMap = formulaAttMap;
    }

    public Map<String, String> getFiFormulaAttMap() {
        return this.fiFormulaAttMap;
    }

    public void setFiFormulaAttMap(Map<String, String> fiFormulaAttMap) {
        this.fiFormulaAttMap = fiFormulaAttMap;
    }

    public Map<String, String> getPrintAttMap() {
        return this.printAttMap;
    }

    public void setPrintAttMap(Map<String, String> printAttMap) {
        this.printAttMap = printAttMap;
    }
}

