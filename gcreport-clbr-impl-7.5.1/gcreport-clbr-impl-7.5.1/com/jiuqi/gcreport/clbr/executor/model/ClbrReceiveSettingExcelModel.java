/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.excel.annotation.Excel
 *  com.jiuqi.common.expimp.dataexport.excel.annotation.ExcelColumn
 */
package com.jiuqi.gcreport.clbr.executor.model;

import com.jiuqi.common.expimp.dataexport.excel.annotation.Excel;
import com.jiuqi.common.expimp.dataexport.excel.annotation.ExcelColumn;

@Excel(title="\u5f85\u529e\u6d88\u606f\u914d\u7f6eExcel\u4fe1\u606f")
public class ClbrReceiveSettingExcelModel {
    @ExcelColumn(index=0, title={"\u63a5\u6536\u65b9\u4e1a\u52a1\u7c7b\u578b\u4ee3\u7801(*\u5fc5\u586b)"})
    private String oppClbrTypes;
    @ExcelColumn(index=1, title={"\u63a5\u6536\u65b9\u4e1a\u52a1\u7c7b\u578b\u540d\u79f0"})
    private String oppClbrTypesName;
    @ExcelColumn(index=2, title={"\u63a5\u6536\u65b9\u4ee3\u7801(*\u5fc5\u586b)"})
    private String oppRelation;
    @ExcelColumn(index=3, title={"\u63a5\u6536\u65b9\u540d\u79f0"})
    private String oppRelationName;
    @ExcelColumn(index=4, title={"\u90e8\u95e8"})
    private String department;
    @ExcelColumn(index=5, title={"\u89d2\u8272"})
    private String roleCodes;
    @ExcelColumn(index=6, title={"\u7528\u6237"})
    private String userNames;

    public String getOppClbrTypes() {
        return this.oppClbrTypes;
    }

    public void setOppClbrTypes(String oppClbrTypes) {
        this.oppClbrTypes = oppClbrTypes;
    }

    public String getOppRelation() {
        return this.oppRelation;
    }

    public void setOppRelation(String oppRelation) {
        this.oppRelation = oppRelation;
    }

    public String getRoleCodes() {
        return this.roleCodes;
    }

    public void setRoleCodes(String roleCodes) {
        this.roleCodes = roleCodes;
    }

    public String getUserNames() {
        return this.userNames;
    }

    public void setUserNames(String userNames) {
        this.userNames = userNames;
    }

    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getOppClbrTypesName() {
        return this.oppClbrTypesName;
    }

    public void setOppClbrTypesName(String oppClbrTypesName) {
        this.oppClbrTypesName = oppClbrTypesName;
    }

    public String getOppRelationName() {
        return this.oppRelationName;
    }

    public void setOppRelationName(String oppRelationName) {
        this.oppRelationName = oppRelationName;
    }
}

