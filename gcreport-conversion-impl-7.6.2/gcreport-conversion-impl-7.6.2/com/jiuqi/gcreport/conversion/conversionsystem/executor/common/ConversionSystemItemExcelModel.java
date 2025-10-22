/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.excel.annotation.Excel
 *  com.jiuqi.common.expimp.dataexport.excel.annotation.ExcelColumn
 */
package com.jiuqi.gcreport.conversion.conversionsystem.executor.common;

import com.jiuqi.common.expimp.dataexport.excel.annotation.Excel;
import com.jiuqi.common.expimp.dataexport.excel.annotation.ExcelColumn;

@Excel(title="\u6298\u7b97\u4f53\u7cfb\u4fe1\u606f")
public class ConversionSystemItemExcelModel {
    @ExcelColumn(index=0, title={"\u62a5\u8868\u4efb\u52a1\u6807\u9898"})
    private String taskTitle;
    @ExcelColumn(index=1, title={"\u62a5\u8868\u4efb\u52a1\u4ee3\u7801"})
    private String taskCode;
    @ExcelColumn(index=2, title={"\u62a5\u8868\u65b9\u6848\u6807\u9898"})
    private String schemeTitle;
    @ExcelColumn(index=3, title={"\u62a5\u8868\u65b9\u6848\u4ee3\u7801"})
    private String schemeCode;
    @ExcelColumn(index=4, title={"\u62a5\u8868\u8868\u5355\u6807\u9898"})
    private String formTitle;
    @ExcelColumn(index=5, title={"\u62a5\u8868\u8868\u5355\u4ee3\u7801"})
    private String formCode;
    @ExcelColumn(index=6, title={"\u62a5\u8868\u533a\u57df\u4ee3\u7801"})
    private String regionCode;
    @ExcelColumn(index=7, title={"\u62a5\u8868\u6307\u6807\u6807\u9898"})
    private String indexTitle;
    @ExcelColumn(index=8, title={"\u6307\u6807\u4ee3\u7801"})
    private String indexCode;
    @ExcelColumn(index=9, title={"\u6307\u6807\u6240\u5c5e\u8868"})
    private String indexTableName;
    @ExcelColumn(index=10, title={"\u6c47\u7387\u7c7b\u578b"})
    private String rateTypeTitle;
    @ExcelColumn(index=11, title={"\u6c47\u7387\u516c\u5f0f"})
    private String rateFormula;

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getSchemeTitle() {
        return this.schemeTitle;
    }

    public void setSchemeTitle(String schemeTitle) {
        this.schemeTitle = schemeTitle;
    }

    public String getFormTitle() {
        return this.formTitle;
    }

    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle;
    }

    public String getIndexTitle() {
        return this.indexTitle;
    }

    public void setIndexTitle(String indexTitle) {
        this.indexTitle = indexTitle;
    }

    public String getIndexCode() {
        return this.indexCode;
    }

    public void setIndexCode(String indexCode) {
        this.indexCode = indexCode;
    }

    public String getRateTypeTitle() {
        return this.rateTypeTitle;
    }

    public void setRateTypeTitle(String rateTypeTitle) {
        this.rateTypeTitle = rateTypeTitle;
    }

    public String getRateFormula() {
        return this.rateFormula;
    }

    public void setRateFormula(String rateFormula) {
        this.rateFormula = rateFormula;
    }

    public String getTaskCode() {
        return this.taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getSchemeCode() {
        return this.schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getIndexTableName() {
        return this.indexTableName;
    }

    public void setIndexTableName(String indexTableName) {
        this.indexTableName = indexTableName;
    }

    public String getRegionCode() {
        return this.regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }
}

