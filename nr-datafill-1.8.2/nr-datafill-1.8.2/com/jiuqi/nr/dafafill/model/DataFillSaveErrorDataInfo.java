/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.importdata.ResultErrorInfo
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.dafafill.model;

import com.jiuqi.nr.common.importdata.ResultErrorInfo;
import com.jiuqi.nr.dafafill.model.DFDimensionValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;

@ApiModel(value="DataFillSaveErrorDataInfo", description="\u9519\u8bef\u4fe1\u606f")
public class DataFillSaveErrorDataInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="\u9519\u8bef\u4fe1\u606f", name="dataError")
    private ResultErrorInfo dataError;
    @ApiModelProperty(value="\u7ef4\u5ea6\u5217\u8868", name="dimensionValues")
    private List<DFDimensionValue> dimensionValues;
    @ApiModelProperty(value="\u6307\u6807", name="zb")
    private String zb;
    @ApiModelProperty(value="\u503c", name="value")
    private Serializable value;
    @ApiModelProperty(value="\u9519\u8bef\u4f4d\u7f6ex", name="errorLocX")
    private Integer errorLocX;
    @ApiModelProperty(value="\u9519\u8bef\u4f4d\u7f6ey", name="errorLocY")
    private Integer errorLocY;

    public ResultErrorInfo getDataError() {
        return this.dataError;
    }

    public void setDataError(ResultErrorInfo dataError) {
        this.dataError = dataError;
    }

    public List<DFDimensionValue> getDimensionValues() {
        return this.dimensionValues;
    }

    public void setDimensionValues(List<DFDimensionValue> dimensionValues) {
        this.dimensionValues = dimensionValues;
    }

    public String getZb() {
        return this.zb;
    }

    public void setZb(String zb) {
        this.zb = zb;
    }

    public Serializable getValue() {
        return this.value;
    }

    public void setValue(Serializable value) {
        this.value = value;
    }

    public Integer getErrorLocX() {
        return this.errorLocX;
    }

    public void setErrorLocX(Integer x) {
        this.errorLocX = x;
    }

    public Integer getErrorLocY() {
        return this.errorLocY;
    }

    public void setErrorLocY(Integer y) {
        this.errorLocY = y;
    }
}

