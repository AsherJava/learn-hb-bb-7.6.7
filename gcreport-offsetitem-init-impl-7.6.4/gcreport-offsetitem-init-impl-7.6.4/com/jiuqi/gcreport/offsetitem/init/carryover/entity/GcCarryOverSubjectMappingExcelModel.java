/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.excel.annotation.Excel
 *  com.jiuqi.common.expimp.dataexport.excel.annotation.ExcelColumn
 */
package com.jiuqi.gcreport.offsetitem.init.carryover.entity;

import com.jiuqi.common.expimp.dataexport.excel.annotation.Excel;
import com.jiuqi.common.expimp.dataexport.excel.annotation.ExcelColumn;

@Excel(title="\u5e74\u7ed3\u79d1\u76ee\u6620\u5c04")
public class GcCarryOverSubjectMappingExcelModel {
    @ExcelColumn(index=0, title={"\u79d1\u76ee\u4ee3\u7801"})
    private String srcSubjectCode;
    @ExcelColumn(index=1, title={"\u6620\u5c04\u79d1\u76ee\u4ee3\u7801"})
    private String destSubjectCode;

    public GcCarryOverSubjectMappingExcelModel() {
    }

    public GcCarryOverSubjectMappingExcelModel(String srcSubjectCode, String destSubjectCode) {
        this.srcSubjectCode = srcSubjectCode;
        this.destSubjectCode = destSubjectCode;
    }

    public String getSrcSubjectCode() {
        return this.srcSubjectCode;
    }

    public void setSrcSubjectCode(String srcSubjectCode) {
        this.srcSubjectCode = srcSubjectCode;
    }

    public String getDestSubjectCode() {
        return this.destSubjectCode;
    }

    public void setDestSubjectCode(String destSubjectCode) {
        this.destSubjectCode = destSubjectCode;
    }
}

