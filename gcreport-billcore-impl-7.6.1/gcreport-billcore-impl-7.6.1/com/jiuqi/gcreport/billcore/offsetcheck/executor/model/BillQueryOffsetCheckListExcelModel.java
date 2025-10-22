/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.excel.annotation.Excel
 *  com.jiuqi.common.expimp.dataexport.excel.annotation.ExcelColumn
 */
package com.jiuqi.gcreport.billcore.offsetcheck.executor.model;

import com.jiuqi.common.expimp.dataexport.excel.annotation.Excel;
import com.jiuqi.common.expimp.dataexport.excel.annotation.ExcelColumn;
import java.util.List;

@Excel(title="\u62b5\u9500\u68c0\u67e5\u7ed3\u679c")
public class BillQueryOffsetCheckListExcelModel {
    @ExcelColumn(index=0, title={"\u5408\u5e76\u89c4\u5219", "\u5408\u5e76\u89c4\u5219"})
    private String ruleTitle;
    @ExcelColumn(index=1, title={"\u7c7b\u578b", "\u7c7b\u578b"})
    private String ruleTypeTitle;
    @ExcelColumn(index=2, title={"\u68c0\u67e5\u7ed3\u679c", "\u68c0\u67e5\u7ed3\u679c"})
    private String checkInfo;
    @ExcelColumn(index=3, title={"\u9002\u7528\u6761\u4ef6", "\u9002\u7528\u6761\u4ef6"})
    private String ruleApplyConditon;
    @ExcelColumn(index=4, title={"\u79d1\u76ee", "\u79d1\u76ee"})
    private String subjectTitle;
    @ExcelColumn(index=5, title={"\u516c\u5f0f", "\u516c\u5f0f"})
    private String formula;
    @ExcelColumn(index=6, title={"\u68c0\u67e5\u751f\u6210\u91d1\u989d", "\u501f\u65b9\u91d1\u989d"})
    private String checkOffsetDebitInfo;
    @ExcelColumn(index=7, title={"\u68c0\u67e5\u751f\u6210\u91d1\u989d", "\u8d37\u65b9\u91d1\u989d"})
    private String checkOffsetCreditInfo;
    @ExcelColumn(index=8, title={"\u62b5\u9500\u5206\u5f55", "\u501f\u65b9\u91d1\u989d"})
    private String offsetDebitInfo;
    @ExcelColumn(index=9, title={"\u62b5\u9500\u5206\u5f55", "\u8d37\u65b9\u91d1\u989d"})
    private String offsetCreditInfo;
    @ExcelColumn(index=10, title={"\u5dee\u5f02", "\u501f\u65b9\u91d1\u989d"})
    private String checkDebitDiffInfo;
    @ExcelColumn(index=11, title={"\u5dee\u5f02", "\u8d37\u65b9\u91d1\u989d"})
    private String checkCreditDiffInfo;
    private List<BillQueryOffsetCheckListExcelModel> children;

    public String getRuleTitle() {
        return this.ruleTitle;
    }

    public void setRuleTitle(String ruleTitle) {
        this.ruleTitle = ruleTitle;
    }

    public String getCheckInfo() {
        return this.checkInfo;
    }

    public void setCheckInfo(String checkInfo) {
        this.checkInfo = checkInfo;
    }

    public String getSubjectTitle() {
        return this.subjectTitle;
    }

    public void setSubjectTitle(String subjectTitle) {
        this.subjectTitle = subjectTitle;
    }

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getCheckOffsetDebitInfo() {
        return this.checkOffsetDebitInfo;
    }

    public void setCheckOffsetDebitInfo(String checkOffsetDebitInfo) {
        this.checkOffsetDebitInfo = checkOffsetDebitInfo;
    }

    public String getCheckOffsetCreditInfo() {
        return this.checkOffsetCreditInfo;
    }

    public void setCheckOffsetCreditInfo(String checkOffsetCreditInfo) {
        this.checkOffsetCreditInfo = checkOffsetCreditInfo;
    }

    public String getOffsetDebitInfo() {
        return this.offsetDebitInfo;
    }

    public void setOffsetDebitInfo(String offsetDebitInfo) {
        this.offsetDebitInfo = offsetDebitInfo;
    }

    public String getOffsetCreditInfo() {
        return this.offsetCreditInfo;
    }

    public void setOffsetCreditInfo(String offsetCreditInfo) {
        this.offsetCreditInfo = offsetCreditInfo;
    }

    public String getRuleTypeTitle() {
        return this.ruleTypeTitle;
    }

    public void setRuleTypeTitle(String ruleTypeTitle) {
        this.ruleTypeTitle = ruleTypeTitle;
    }

    public String getRuleApplyConditon() {
        return this.ruleApplyConditon;
    }

    public void setRuleApplyConditon(String ruleApplyConditon) {
        this.ruleApplyConditon = ruleApplyConditon;
    }

    public List<BillQueryOffsetCheckListExcelModel> getChildren() {
        return this.children;
    }

    public void setChildren(List<BillQueryOffsetCheckListExcelModel> children) {
        this.children = children;
    }

    public String getCheckDebitDiffInfo() {
        return this.checkDebitDiffInfo;
    }

    public void setCheckDebitDiffInfo(String checkDebitDiffInfo) {
        this.checkDebitDiffInfo = checkDebitDiffInfo;
    }

    public String getCheckCreditDiffInfo() {
        return this.checkCreditDiffInfo;
    }

    public void setCheckCreditDiffInfo(String checkCreditDiffInfo) {
        this.checkCreditDiffInfo = checkCreditDiffInfo;
    }
}

