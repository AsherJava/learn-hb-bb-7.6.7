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

@Excel(title="\u4e1a\u52a1\u534f\u540c\u603b\u89c8\u4fe1\u606f")
public class ClbrBillPandectDetailsExcelModel {
    @ExcelColumn(index=0, title={"\u5355\u4f4d\u540d\u79f0"})
    private String relation;
    @ExcelColumn(index=1, title={"\u603b\u6570"})
    private Integer total;
    @ExcelColumn(index=2, title={"\u5df2\u786e\u8ba4"})
    private Integer confirmCount;
    @ExcelColumn(index=3, title={"\u5df2\u786e\u8ba4\u7387"})
    private double confirmRate;
    @ExcelColumn(index=4, title={"\u90e8\u5206\u786e\u8ba4"})
    private Integer partConfirmCount;
    @ExcelColumn(index=5, title={"\u90e8\u5206\u786e\u8ba4\u7387"})
    private double partConfirmRate;
    @ExcelColumn(index=6, title={"\u672a\u786e\u8ba4"})
    private Integer notConfirmCount;
    @ExcelColumn(index=7, title={"\u672a\u786e\u8ba4\u7387"})
    private double notConfirmRate;
    @ExcelColumn(index=8, title={"\u5df2\u9a73\u56de"})
    private Integer rejectCount;
    @ExcelColumn(index=9, title={"\u5df2\u9a73\u56de\u7387"})
    private double rejectRate;
    @ExcelColumn(index=10, title={"\u5df2\u786e\u8ba4\u91d1\u989d"})
    private double confirmAmount;
    @ExcelColumn(index=11, title={"\u672a\u786e\u8ba4\u91d1\u989d"})
    private double notConfirmAmount;

    public String getRelation() {
        return this.relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public Integer getTotal() {
        return this.total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getConfirmCount() {
        return this.confirmCount;
    }

    public void setConfirmCount(Integer confirmCount) {
        this.confirmCount = confirmCount;
    }

    public double getConfirmRate() {
        return this.confirmRate;
    }

    public void setConfirmRate(double confirmRate) {
        this.confirmRate = confirmRate;
    }

    public Integer getPartConfirmCount() {
        return this.partConfirmCount;
    }

    public void setPartConfirmCount(Integer partConfirmCount) {
        this.partConfirmCount = partConfirmCount;
    }

    public double getPartConfirmRate() {
        return this.partConfirmRate;
    }

    public void setPartConfirmRate(double partConfirmRate) {
        this.partConfirmRate = partConfirmRate;
    }

    public Integer getNotConfirmCount() {
        return this.notConfirmCount;
    }

    public void setNotConfirmCount(Integer notConfirmCount) {
        this.notConfirmCount = notConfirmCount;
    }

    public double getNotConfirmRate() {
        return this.notConfirmRate;
    }

    public void setNotConfirmRate(double notConfirmRate) {
        this.notConfirmRate = notConfirmRate;
    }

    public Integer getRejectCount() {
        return this.rejectCount;
    }

    public void setRejectCount(Integer rejectCount) {
        this.rejectCount = rejectCount;
    }

    public double getRejectRate() {
        return this.rejectRate;
    }

    public void setRejectRate(double rejectRate) {
        this.rejectRate = rejectRate;
    }

    public double getConfirmAmount() {
        return this.confirmAmount;
    }

    public void setConfirmAmount(double confirmAmount) {
        this.confirmAmount = confirmAmount;
    }

    public double getNotConfirmAmount() {
        return this.notConfirmAmount;
    }

    public void setNotConfirmAmount(double notConfirmAmount) {
        this.notConfirmAmount = notConfirmAmount;
    }
}

