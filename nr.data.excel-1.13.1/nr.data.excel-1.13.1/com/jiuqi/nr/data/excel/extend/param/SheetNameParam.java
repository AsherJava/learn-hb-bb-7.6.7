/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.data.excel.extend.param;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;

public class SheetNameParam {
    private final DimensionCombination curMasterKey;
    private final String formKey;
    private final int page;
    private final String excelName;

    public SheetNameParam(DimensionCombination curMasterKey, String formKey, int page, String excelName) {
        this.curMasterKey = curMasterKey;
        this.formKey = formKey;
        this.page = page;
        this.excelName = excelName;
    }

    public DimensionCombination getCurMasterKey() {
        return this.curMasterKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public int getPage() {
        return this.page;
    }

    public String getExcelName() {
        return this.excelName;
    }
}

