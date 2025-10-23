/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formula.utils.excel.entity;

import com.jiuqi.nr.formula.utils.excel.core.ExcelEntity;
import com.jiuqi.nr.formula.utils.excel.core.ExcelField;
import com.jiuqi.nr.formula.utils.excel.core.ExcelProperty;

@ExcelProperty(sheetName="\u8868\u95f4\u516c\u5f0f")
public class BetweenFormulaExcelEntity
implements ExcelEntity {
    @ExcelField(columnName="\u7f16\u53f7", order=1)
    private String no;
    @ExcelField(columnName="\u8868\u8fbe\u5f0f", order=2)
    private String expression;
    @ExcelField(columnName="\u8bf4\u660e", order=3)
    private String description;
    @ExcelField(columnName="\u7c7b\u578b", order=4)
    private String type;
    @ExcelField(columnName="\u5ba1\u6838\u7c7b\u578b", order=5)
    private String auditType;
    @ExcelField(columnName="\u8c03\u6574\u6307\u6807", order=6)
    private String adjustIndicator;

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
        return this.type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getAuditType() {
        return this.auditType;
    }

    @Override
    public void setAuditType(String auditType) {
        this.auditType = auditType;
    }

    @Override
    public String getAdjustIndicator() {
        return this.adjustIndicator;
    }

    @Override
    public void setAdjustIndicator(String adjustIndicator) {
        this.adjustIndicator = adjustIndicator;
    }

    @Override
    public ExcelEntity clone() {
        BetweenFormulaExcelEntity excelEntity = new BetweenFormulaExcelEntity();
        excelEntity.setNo(this.no);
        excelEntity.setExpression(this.expression);
        excelEntity.setDescription(this.description);
        excelEntity.setType(this.type);
        excelEntity.setAuditType(this.auditType);
        excelEntity.setAdjustIndicator(this.adjustIndicator);
        return excelEntity;
    }
}

