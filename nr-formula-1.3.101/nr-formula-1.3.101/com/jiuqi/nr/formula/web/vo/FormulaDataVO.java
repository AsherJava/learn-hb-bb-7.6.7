/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formula.web.vo;

import com.jiuqi.nr.formula.dto.FormulaDTO;
import java.util.List;

public class FormulaDataVO {
    private List<FormulaDTO> rows;
    private List<String> formulaCode;
    private FormulaDTO lastFormula;
    private FormulaDTO nextFormula;
    private String maxCode;
    private Integer total;
    private Integer newPage;

    public List<FormulaDTO> getRows() {
        return this.rows;
    }

    public void setRows(List<FormulaDTO> rows) {
        this.rows = rows;
    }

    public List<String> getFormulaCode() {
        return this.formulaCode;
    }

    public void setFormulaCode(List<String> formulaCode) {
        this.formulaCode = formulaCode;
    }

    public String getMaxCode() {
        return this.maxCode;
    }

    public void setMaxCode(String maxCode) {
        this.maxCode = maxCode;
    }

    public Integer getTotal() {
        return this.total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public FormulaDTO getLastFormula() {
        return this.lastFormula;
    }

    public void setLastFormula(FormulaDTO lastFormula) {
        this.lastFormula = lastFormula;
    }

    public FormulaDTO getNextFormula() {
        return this.nextFormula;
    }

    public void setNextFormula(FormulaDTO nextFormula) {
        this.nextFormula = nextFormula;
    }

    public Integer getNewPage() {
        return this.newPage;
    }

    public void setNewPage(Integer newPage) {
        this.newPage = newPage;
    }
}

