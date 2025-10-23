/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formula.web.param;

import com.jiuqi.nr.formula.dto.FormulaDTO;
import com.jiuqi.nr.formula.web.param.BasePM;
import java.util.List;

public class FormulaSavePM
extends BasePM {
    private List<FormulaDTO> itemList;
    private String unit;

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<FormulaDTO> getItemList() {
        return this.itemList;
    }

    public void setItemList(List<FormulaDTO> itemList) {
        this.itemList = itemList;
    }
}

