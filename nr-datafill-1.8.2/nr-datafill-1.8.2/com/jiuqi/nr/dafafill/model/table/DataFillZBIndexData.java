/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.dafafill.model.table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.nr.dafafill.model.FieldFormat;
import com.jiuqi.nr.dafafill.model.enums.CellType;
import com.jiuqi.nr.dafafill.model.enums.EditorType;
import com.jiuqi.nr.dafafill.model.table.DataFillIndexData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel(value="DataFillZBIndex", description="\u6307\u6807\u7d22\u5f15")
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class DataFillZBIndexData
extends DataFillIndexData
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="\u524d\u7aef\u663e\u793a\u683c\u5f0f")
    private String format;
    @ApiModelProperty(value="\u663e\u793a\u683c\u5f0f")
    @JsonIgnore
    private FieldFormat fieldFormat;
    @ApiModelProperty(value="\u5355\u5143\u683c\u7c7b\u578b")
    private CellType cellType;
    @ApiModelProperty(value="\u7f16\u8f91\u5668")
    private EditorType editor;
    @ApiModelProperty(value="\u6570\u636e\u6765\u6e90")
    private String source;
    @ApiModelProperty(value="\u516c\u5f0f")
    private String expression;
    @ApiModelProperty(value="\u5c0f\u6570\u4f4d\u6570")
    private int decimal;
    private Boolean isMultiVal = Boolean.FALSE;

    public Boolean getisMultiVal() {
        return this.isMultiVal;
    }

    public void setisMultiVal(Boolean multiVal) {
        this.isMultiVal = multiVal;
    }

    public String getFormat() {
        return this.format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public CellType getCellType() {
        return this.cellType;
    }

    public void setCellType(CellType cellType) {
        this.cellType = cellType;
    }

    public EditorType getEditor() {
        return this.editor;
    }

    public void setEditor(EditorType editor) {
        this.editor = editor;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public FieldFormat getFieldFormat() {
        return this.fieldFormat;
    }

    public void setFieldFormat(FieldFormat fieldFormat) {
        this.fieldFormat = fieldFormat;
    }

    public int getDecimal() {
        return this.decimal;
    }

    public void setDecimal(int decimal) {
        this.decimal = decimal;
    }
}

