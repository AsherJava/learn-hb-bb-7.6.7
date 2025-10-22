/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.data.excel.param;

import com.jiuqi.nr.data.excel.param.DataInfo;
import com.jiuqi.nr.data.excel.param.Excel;
import com.jiuqi.nr.data.excel.param.SheetType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;

public class Sheet
extends DataInfo {
    private Excel excel;
    private String sheetName;
    private SheetType type;

    public Sheet(DimensionCombination dimensionCombination, String formKey, Excel excel, String sheetName, SheetType type) {
        super(dimensionCombination, formKey);
        this.excel = excel;
        this.sheetName = sheetName;
        this.type = type;
    }

    public Excel getExcel() {
        return this.excel;
    }

    public void setExcel(Excel excel) {
        this.excel = excel;
    }

    public String getSheetName() {
        return this.sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public SheetType getType() {
        return this.type;
    }

    public void setType(SheetType type) {
        this.type = type;
    }

    public int hashCode() {
        return this.toString().hashCode();
    }

    public String toString() {
        return this.excel + this.sheetName;
    }

    public boolean equals(Object obj) {
        return obj != null && obj.hashCode() == this.hashCode();
    }
}

