/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.dto;

import com.jiuqi.nr.task.form.dto.FormCopySchemeVO;
import java.util.List;
import java.util.Map;

public class FormCopyLinksVO {
    private List<FormCopySchemeVO> srcFormulaSchemes;
    private List<FormCopySchemeVO> srcPrintFormulaSchemes;
    private List<FormCopySchemeVO> desFormulaSchemes;
    private List<FormCopySchemeVO> desPrintFormulaSchemes;
    private Map<String, String> formulaMap;
    private Map<String, String> printMap;

    public List<FormCopySchemeVO> getSrcFormulaSchemes() {
        return this.srcFormulaSchemes;
    }

    public void setSrcFormulaSchemes(List<FormCopySchemeVO> srcFormulaSchemes) {
        this.srcFormulaSchemes = srcFormulaSchemes;
    }

    public List<FormCopySchemeVO> getSrcPrintFormulaSchemes() {
        return this.srcPrintFormulaSchemes;
    }

    public void setSrcPrintFormulaSchemes(List<FormCopySchemeVO> srcPrintFormulaSchemes) {
        this.srcPrintFormulaSchemes = srcPrintFormulaSchemes;
    }

    public List<FormCopySchemeVO> getDesFormulaSchemes() {
        return this.desFormulaSchemes;
    }

    public void setDesFormulaSchemes(List<FormCopySchemeVO> desFormulaSchemes) {
        this.desFormulaSchemes = desFormulaSchemes;
    }

    public List<FormCopySchemeVO> getDesPrintFormulaSchemes() {
        return this.desPrintFormulaSchemes;
    }

    public void setDesPrintFormulaSchemes(List<FormCopySchemeVO> desPrintFormulaSchemes) {
        this.desPrintFormulaSchemes = desPrintFormulaSchemes;
    }

    public Map<String, String> getFormulaMap() {
        return this.formulaMap;
    }

    public void setFormulaMap(Map<String, String> formulaMap) {
        this.formulaMap = formulaMap;
    }

    public Map<String, String> getPrintMap() {
        return this.printMap;
    }

    public void setPrintMap(Map<String, String> printMap) {
        this.printMap = printMap;
    }
}

