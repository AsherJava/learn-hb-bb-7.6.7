/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.type.DataFieldGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.internal.anno.DBAnno$DBField
 *  com.jiuqi.nr.datascheme.internal.anno.DBAnno$DBTable
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.summary.internal.entity;

import com.jiuqi.nr.datascheme.api.type.DataFieldGatherType;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.internal.anno.DBAnno;
import com.jiuqi.nr.summary.api.Ordered;
import com.jiuqi.nr.summary.api.SummaryDataCell;
import java.sql.Timestamp;
import java.time.Instant;
import org.jetbrains.annotations.NotNull;

@DBAnno.DBTable(dbTable="NR_SUMMARY_DATACELL")
public class SummaryDataCellDO
implements SummaryDataCell {
    @DBAnno.DBField(dbField="SDC_KEY", isPk=true)
    protected String key;
    @DBAnno.DBField(dbField="SR_KEY")
    protected String reportKey;
    @DBAnno.DBField(dbField="SDC_X")
    protected int x;
    @DBAnno.DBField(dbField="SDC_Y")
    protected int y;
    @DBAnno.DBField(dbField="SDC_ROW_NUM")
    protected int rowNum;
    @DBAnno.DBField(dbField="SDC_COL_NUM")
    protected int colNum;
    @DBAnno.DBField(dbField="SDC_EXPRESSION")
    protected String expression;
    @DBAnno.DBField(dbField="SDC_EXPRESSION_TITLE")
    protected String expressionTitle;
    @DBAnno.DBField(dbField="SDC_FIELD_KEY")
    protected String referDataFieldKey;
    @DBAnno.DBField(dbField="SDC_SUM_MODE", tranWith="transDataFieldGatherType", dbType=Integer.class, appType=DataFieldGatherType.class)
    protected DataFieldGatherType gatherType;
    @DBAnno.DBField(dbField="SDC_MODIFY_TIME", tranWith="transInstant", dbType=Timestamp.class, appType=Instant.class)
    protected Instant modifyTime;
    @DBAnno.DBField(dbField="SDC_FIELD_NAME")
    protected String fieldName;
    @DBAnno.DBField(dbField="SDC_FIELD_TITLE")
    protected String fieldTitle;
    @DBAnno.DBField(dbField="SDC_FIELD_TABLE")
    protected String dataTableKey;
    @DBAnno.DBField(dbField="SDC_DATATYPE", tranWith="transDataFieldType", dbType=Integer.class, appType=DataFieldType.class)
    protected DataFieldType fieldType;
    @DBAnno.DBField(dbField="SDC_PRECISION")
    protected int precision;
    @DBAnno.DBField(dbField="SDC_DECIMAL")
    protected int decimal;

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

    @Override
    public Instant getModifyTime() {
        return this.modifyTime;
    }

    @Override
    public String getOrder() {
        return null;
    }

    @Override
    public String getReportKey() {
        return this.reportKey;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public int getRowNum() {
        return this.rowNum;
    }

    @Override
    public int getColNum() {
        return this.colNum;
    }

    @Override
    public String getExpression() {
        return this.expression;
    }

    @Override
    public String getExpressionTitle() {
        return this.expressionTitle;
    }

    @Override
    public String getReferDataFieldKey() {
        return this.referDataFieldKey;
    }

    @Override
    public DataFieldGatherType getGatherType() {
        return this.gatherType;
    }

    @Override
    public String getFieldName() {
        return this.fieldName;
    }

    @Override
    public String getFieldTitle() {
        return this.fieldTitle;
    }

    @Override
    public String getDataTableKey() {
        return this.dataTableKey;
    }

    @Override
    public DataFieldType getFieldType() {
        return this.fieldType;
    }

    @Override
    public int getPrecision() {
        return this.precision;
    }

    @Override
    public int getDecimal() {
        return this.decimal;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setReportKey(String reportKey) {
        this.reportKey = reportKey;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public void setColNum(int colNum) {
        this.colNum = colNum;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public void setExpressionTitle(String expressionTitle) {
        this.expressionTitle = expressionTitle;
    }

    public void setReferDataFieldKey(String referDataFieldKey) {
        this.referDataFieldKey = referDataFieldKey;
    }

    public void setGatherType(DataFieldGatherType gatherType) {
        this.gatherType = gatherType;
    }

    public void setModifyTime(Instant modifyTime) {
        this.modifyTime = modifyTime;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setFieldTitle(String fieldTitle) {
        this.fieldTitle = fieldTitle;
    }

    public void setDataTableKey(String dataTableKey) {
        this.dataTableKey = dataTableKey;
    }

    public void setFieldType(DataFieldType fieldType) {
        this.fieldType = fieldType;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public void setDecimal(int decimal) {
        this.decimal = decimal;
    }

    @Override
    public int compareTo(@NotNull Ordered o) {
        return 0;
    }
}

