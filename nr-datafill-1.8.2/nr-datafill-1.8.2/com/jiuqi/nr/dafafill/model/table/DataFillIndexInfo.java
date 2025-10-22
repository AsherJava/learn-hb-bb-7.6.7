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
import com.jiuqi.nr.dafafill.model.enums.ColWidthType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel(value="DataFillIndexInfo", description="\u7d22\u5f15\u7684\u5176\u4ed6\u4fe1\u606f")
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class DataFillIndexInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonIgnore
    @ApiModelProperty(value="\u6807\u9898")
    private String name;
    @ApiModelProperty(value="\u7ef4\u5ea6\u540d")
    private String dimensionName;
    @ApiModelProperty(value="\u662f\u5426\u9690\u85cf")
    private Boolean hide;
    @ApiModelProperty(value="\u5217\u5bbd\u7c7b\u578b")
    private ColWidthType colWidthType;
    @ApiModelProperty(value="\u81ea\u5b9a\u5217\u5bbd\uff0c\u4ec5\u5217\u5bbd\u7c7b\u578b\u4e3a\u81ea\u5b9a\u4e49\u65f6\u6709\u6548")
    private Integer colWidth;
    @ApiModelProperty(value="\u662f\u5426\u53ea\u8bfb")
    private Boolean readOnly;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDimensionName() {
        return this.dimensionName;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }

    public ColWidthType getColWidthType() {
        return this.colWidthType;
    }

    public void setColWidthType(ColWidthType colWidthType) {
        this.colWidthType = colWidthType;
    }

    public Integer getColWidth() {
        return this.colWidth;
    }

    public void setColWidth(Integer colWidth) {
        this.colWidth = colWidth;
    }

    public Boolean getReadOnly() {
        return this.readOnly;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }

    public Boolean getHide() {
        return this.hide;
    }

    public void setHide(Boolean hide) {
        this.hide = hide;
    }
}

