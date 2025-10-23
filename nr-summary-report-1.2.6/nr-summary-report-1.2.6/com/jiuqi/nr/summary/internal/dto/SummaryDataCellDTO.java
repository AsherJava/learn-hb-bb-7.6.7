/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.type.DataFieldGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.summary.internal.dto;

import com.jiuqi.nr.datascheme.api.type.DataFieldGatherType;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.summary.api.Ordered;
import com.jiuqi.nr.summary.api.SummaryDataCell;
import java.time.Instant;
import org.jetbrains.annotations.NotNull;

public class SummaryDataCellDTO
implements SummaryDataCell {
    protected String key;
    protected String reportKey;
    protected int x;
    protected int y;
    protected int rowNum;
    protected int colNum;
    protected String expression;
    protected String expressionTitle;
    protected String referDataFieldKey;
    protected DataFieldGatherType gatherType;
    protected Instant modifyTime;
    protected String fieldName;
    protected String fieldTitle;
    protected String dataTableKey;
    protected DataFieldType fieldType;
    protected int precision;
    protected int decimal;

    public SummaryDataCellDTO() {
    }

    public SummaryDataCellDTO(SummaryDataCellDTO dataCellDTO) {
        this.key = dataCellDTO.getKey();
        this.reportKey = dataCellDTO.getReportKey();
        this.x = dataCellDTO.getX();
        this.y = dataCellDTO.getY();
        this.rowNum = dataCellDTO.getRowNum();
        this.colNum = dataCellDTO.getColNum();
        this.expression = dataCellDTO.getExpression();
        this.expressionTitle = dataCellDTO.getExpressionTitle();
        this.referDataFieldKey = dataCellDTO.getReferDataFieldKey();
        this.gatherType = dataCellDTO.getGatherType();
        this.modifyTime = dataCellDTO.getModifyTime();
        this.fieldName = dataCellDTO.getFieldName();
        this.fieldTitle = dataCellDTO.getFieldTitle();
        this.dataTableKey = dataCellDTO.getDataTableKey();
        this.fieldType = dataCellDTO.getFieldType();
        this.precision = dataCellDTO.getPrecision();
        this.decimal = dataCellDTO.getDecimal();
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getDesc() {
        return null;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getReportKey() {
        return this.reportKey;
    }

    public void setReportKey(String reportKey) {
        this.reportKey = reportKey;
    }

    @Override
    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getRowNum() {
        return this.rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    @Override
    public int getColNum() {
        return this.colNum;
    }

    public void setColNum(int colNum) {
        this.colNum = colNum;
    }

    @Override
    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public String getExpressionTitle() {
        return this.expressionTitle;
    }

    public void setExpressionTitle(String expressionTitle) {
        this.expressionTitle = expressionTitle;
    }

    @Override
    public String getReferDataFieldKey() {
        return this.referDataFieldKey;
    }

    public void setReferDataFieldKey(String referDataFieldKey) {
        this.referDataFieldKey = referDataFieldKey;
    }

    @Override
    public DataFieldGatherType getGatherType() {
        return this.gatherType;
    }

    public void setGatherType(DataFieldGatherType gatherType) {
        this.gatherType = gatherType;
    }

    @Override
    public Instant getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(Instant modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String getFieldTitle() {
        return this.fieldTitle;
    }

    public void setFieldTitle(String fieldTitle) {
        this.fieldTitle = fieldTitle;
    }

    @Override
    public String getDataTableKey() {
        return this.dataTableKey;
    }

    public void setDataTableKey(String dataTableKey) {
        this.dataTableKey = dataTableKey;
    }

    @Override
    public DataFieldType getFieldType() {
        return this.fieldType;
    }

    public void setFieldType(DataFieldType fieldType) {
        this.fieldType = fieldType;
    }

    @Override
    public int getPrecision() {
        return this.precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    @Override
    public int getDecimal() {
        return this.decimal;
    }

    public void setDecimal(int decimal) {
        this.decimal = decimal;
    }

    @Override
    public String getOrder() {
        return null;
    }

    @Override
    public int compareTo(@NotNull Ordered o) {
        return 0;
    }
}

