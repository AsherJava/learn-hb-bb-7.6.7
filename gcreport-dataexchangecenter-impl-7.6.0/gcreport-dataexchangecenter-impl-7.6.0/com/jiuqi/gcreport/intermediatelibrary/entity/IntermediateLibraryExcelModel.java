/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.excel.annotation.Excel
 *  com.jiuqi.common.expimp.dataexport.excel.annotation.ExcelColumn
 */
package com.jiuqi.gcreport.intermediatelibrary.entity;

import com.jiuqi.common.expimp.dataexport.excel.annotation.Excel;
import com.jiuqi.common.expimp.dataexport.excel.annotation.ExcelColumn;

@Excel(title="\u6570\u636e\u4ea4\u6362\u6307\u6807\u6570\u636e")
public class IntermediateLibraryExcelModel {
    @ExcelColumn(index=0, title={"\u5e8f\u53f7"})
    private String index;
    @ExcelColumn(index=1, title={"\u6307\u6807\u4ee3\u7801"})
    private String fieldCode;
    @ExcelColumn(index=2, title={"\u6307\u6807\u540d\u79f0"})
    private String fieldTitle;
    @ExcelColumn(index=3, title={"\u6570\u636e\u7c7b\u578b"})
    private String fieldType;
    @ExcelColumn(index=4, title={"\u6570\u636e\u7cbe\u5ea6"})
    private String fieldSize;

    public String getIndex() {
        return this.index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getFieldCode() {
        return this.fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getFieldTitle() {
        return this.fieldTitle;
    }

    public void setFieldTitle(String fieldTitle) {
        this.fieldTitle = fieldTitle;
    }

    public String getFieldType() {
        return this.fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldSize() {
        return this.fieldSize;
    }

    public void setFieldSize(String fieldSize) {
        this.fieldSize = fieldSize;
    }
}

