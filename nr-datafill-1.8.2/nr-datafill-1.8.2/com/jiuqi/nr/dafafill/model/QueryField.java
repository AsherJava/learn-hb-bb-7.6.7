/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 */
package com.jiuqi.nr.dafafill.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.nr.dafafill.model.FieldFormat;
import com.jiuqi.nr.dafafill.model.enums.ColWidthType;
import com.jiuqi.nr.dafafill.model.enums.EditorType;
import com.jiuqi.nr.dafafill.model.enums.FieldType;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import java.io.Serializable;
import org.springframework.util.StringUtils;

public class QueryField
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String code;
    private String dataSchemeCode;
    private String fullCode;
    private String title;
    private String alias;
    private FieldType fieldType;
    private DataFieldType dataType;
    private String expression;
    private ColWidthType colWidthType;
    private int colWidth = 100;
    private EditorType editorType;
    private FieldFormat showFormat;
    private String minValue;
    private String maxValue;
    private String filterExpression;
    private boolean notEmpty;
    private boolean allowNotLeafNodeRefer = true;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDataSchemeCode() {
        return this.dataSchemeCode;
    }

    public void setDataSchemeCode(String dataSchemeCode) {
        this.dataSchemeCode = dataSchemeCode;
    }

    public String getFullCode() {
        return this.fullCode;
    }

    public void setFullCode(String fullCode) {
        this.fullCode = fullCode;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public FieldType getFieldType() {
        return this.fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public DataFieldType getDataType() {
        return this.dataType;
    }

    public void setDataType(DataFieldType dataType) {
        this.dataType = dataType;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public ColWidthType getColWidthType() {
        return this.colWidthType;
    }

    public void setColWidthType(ColWidthType colWidthType) {
        this.colWidthType = colWidthType;
    }

    public int getColWidth() {
        return this.colWidth;
    }

    public void setColWidth(int colWidth) {
        this.colWidth = colWidth;
    }

    public EditorType getEditorType() {
        return this.editorType;
    }

    public void setEditorType(EditorType editorType) {
        this.editorType = editorType;
    }

    public FieldFormat getShowFormat() {
        return this.showFormat;
    }

    public void setShowFormat(FieldFormat showFormat) {
        this.showFormat = showFormat;
    }

    public String getMinValue() {
        return this.minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public String getMaxValue() {
        return this.maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public boolean isNotEmpty() {
        return this.notEmpty;
    }

    public void setNotEmpty(boolean notEmpty) {
        this.notEmpty = notEmpty;
    }

    @JsonIgnore
    public String getSimplifyFullCode() {
        String[] split = this.fullCode.split("\\.");
        return split.length > 1 ? split[1] : this.fullCode;
    }

    @JsonIgnore
    public String getTableCode() {
        if (this.fieldType.equals((Object)FieldType.MASTER)) {
            return this.id.split("@")[0];
        }
        if (StringUtils.hasLength(this.expression)) {
            return this.expression.split("@")[0];
        }
        return "";
    }

    public String getFilterExpression() {
        return this.filterExpression;
    }

    public void setFilterExpression(String filterExpression) {
        this.filterExpression = filterExpression;
    }

    public boolean isAllowNotLeafNodeRefer() {
        return this.allowNotLeafNodeRefer;
    }

    public void setAllowNotLeafNodeRefer(boolean allowNotLeafNodeRefer) {
        this.allowNotLeafNodeRefer = allowNotLeafNodeRefer;
    }
}

