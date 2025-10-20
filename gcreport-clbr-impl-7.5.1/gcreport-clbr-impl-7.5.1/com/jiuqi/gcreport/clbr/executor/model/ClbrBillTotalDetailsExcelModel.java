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

@Excel(title="\u4e1a\u52a1\u534f\u540c\u603b\u6570\u4fe1\u606f")
public class ClbrBillTotalDetailsExcelModel {
    @ExcelColumn(index=0, title={"\u534f\u540c\u7801"})
    private String clbrCode;
    @ExcelColumn(index=1, title={"\u5355\u636e\u7f16\u53f7"})
    private String clbrBillCode;
    @ExcelColumn(index=2, title={"\u53d1\u8d77\u65b9\u5355\u4f4d"})
    private String relationTitle;
    @ExcelColumn(index=3, title={"\u53d1\u8d77\u65b9\u534f\u540c\u4e1a\u52a1\u7c7b\u578b"})
    private String clbrTypeTitle;
    @ExcelColumn(index=4, title={"\u91d1\u989d"})
    private double amount;
    @ExcelColumn(index=5, title={"\u672a\u786e\u8ba4\u91d1\u989d"})
    private double notConfirmAmount;
    @ExcelColumn(index=6, title={"\u5df2\u786e\u8ba4\u91d1\u989d"})
    private double confirmAmount;
    @ExcelColumn(index=7, title={"\u63a5\u6536\u65b9\u5355\u4f4d"})
    private String oppRelationTitle;
    @ExcelColumn(index=8, title={"\u63a5\u6536\u65b9\u534f\u540c\u4e1a\u52a1\u7c7b\u578b"})
    private String oppClbrTypeTitle;
    @ExcelColumn(index=9, title={"\u534f\u540c\u63a5\u6536\u65b9\u5355\u636e\u7f16\u53f7"})
    private String oppClbrBillCode;
    @ExcelColumn(index=10, title={"\u5355\u636e\u65e5\u671f"})
    private String createTime;
    @ExcelColumn(index=11, title={"\u786e\u8ba4\u65e5\u671f"})
    private String clbrTime;

    public String getClbrCode() {
        return this.clbrCode;
    }

    public void setClbrCode(String clbrCode) {
        this.clbrCode = clbrCode;
    }

    public String getClbrBillCode() {
        return this.clbrBillCode;
    }

    public void setClbrBillCode(String clbrBillCode) {
        this.clbrBillCode = clbrBillCode;
    }

    public String getRelationTitle() {
        return this.relationTitle;
    }

    public void setRelationTitle(String relationTitle) {
        this.relationTitle = relationTitle;
    }

    public String getClbrTypeTitle() {
        return this.clbrTypeTitle;
    }

    public void setClbrTypeTitle(String clbrTypeTitle) {
        this.clbrTypeTitle = clbrTypeTitle;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getNotConfirmAmount() {
        return this.notConfirmAmount;
    }

    public void setNotConfirmAmount(double notConfirmAmount) {
        this.notConfirmAmount = notConfirmAmount;
    }

    public double getConfirmAmount() {
        return this.confirmAmount;
    }

    public void setConfirmAmount(double confirmAmount) {
        this.confirmAmount = confirmAmount;
    }

    public String getOppRelationTitle() {
        return this.oppRelationTitle;
    }

    public void setOppRelationTitle(String oppRelationTitle) {
        this.oppRelationTitle = oppRelationTitle;
    }

    public String getOppClbrTypeTitle() {
        return this.oppClbrTypeTitle;
    }

    public void setOppClbrTypeTitle(String oppClbrTypeTitle) {
        this.oppClbrTypeTitle = oppClbrTypeTitle;
    }

    public String getOppClbrBillCode() {
        return this.oppClbrBillCode;
    }

    public void setOppClbrBillCode(String oppClbrBillCode) {
        this.oppClbrBillCode = oppClbrBillCode;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getClbrTime() {
        return this.clbrTime;
    }

    public void setClbrTime(String clbrTime) {
        this.clbrTime = clbrTime;
    }
}

