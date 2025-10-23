/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.formio.dto;

import com.jiuqi.nr.task.form.formio.dto.ImportBaseDTO;

public class ImportFormulaDTO
extends ImportBaseDTO {
    private String address;
    private String formula;
    private Boolean needReBuild;
    private int formulaType;

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public Boolean getNeedReBuild() {
        return this.needReBuild;
    }

    public void setNeedReBuild(Boolean needReBuild) {
        this.needReBuild = needReBuild;
    }

    public int getFormulaType() {
        return this.formulaType;
    }

    public void setFormulaType(int formulaType) {
        this.formulaType = formulaType;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

