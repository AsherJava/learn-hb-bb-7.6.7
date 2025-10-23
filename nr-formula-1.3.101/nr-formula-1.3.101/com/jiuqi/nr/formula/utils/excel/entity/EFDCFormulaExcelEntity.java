/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formula.utils.excel.entity;

import com.jiuqi.nr.formula.utils.excel.core.ExcelEntity;
import com.jiuqi.nr.formula.utils.excel.core.ExcelField;
import com.jiuqi.nr.formula.utils.excel.core.ExcelProperty;

@ExcelProperty
public class EFDCFormulaExcelEntity
implements ExcelEntity {
    @ExcelField(columnName="\u7f16\u53f7", order=1)
    private String no;
    @ExcelField(columnName="\u8868\u8fbe\u5f0f", order=2)
    private String expression;
    @ExcelField(columnName="\u8bf4\u660e", order=3)
    private String description;

    @Override
    public String getNo() {
        return this.no;
    }

    @Override
    public void setNo(String no) {
        this.no = no;
    }

    @Override
    public String getExpression() {
        return this.expression;
    }

    @Override
    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public String getAuditType() {
        return null;
    }

    @Override
    public String getAdjustIndicator() {
        return null;
    }

    @Override
    public void setType(String type) {
    }

    @Override
    public void setAuditType(String auditType) {
    }

    @Override
    public void setAdjustIndicator(String adjustIndicator) {
    }

    @Override
    public ExcelEntity clone() {
        EFDCFormulaExcelEntity excelEntity = new EFDCFormulaExcelEntity();
        excelEntity.setNo(this.no);
        excelEntity.setExpression(this.expression);
        excelEntity.setDescription(this.description);
        return excelEntity;
    }
}

