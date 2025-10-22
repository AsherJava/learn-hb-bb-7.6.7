/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.dafafill.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.dafafill.model.DFDimensionValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;

@ApiModel(value="DataFillDataSaveRow", description="\u6570\u636e\u4fdd\u5b58\u884c")
@JsonIgnoreProperties(ignoreUnknown=true)
public class DataFillDataSaveRow
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="\u7ef4\u5ea6\u5217\u8868", name="dimensionValues", required=true)
    private List<DFDimensionValue> dimensionValues;
    @ApiModelProperty(value="\u6307\u6807", name="zbs", required=true)
    private List<String> zbs;
    @ApiModelProperty(value="\u6307\u6807\u5bf9\u5e94\u503c", name="values", required=true)
    private List<String> values;

    public List<DFDimensionValue> getDimensionValues() {
        return this.dimensionValues;
    }

    public void setDimensionValues(List<DFDimensionValue> dimensionValues) {
        this.dimensionValues = dimensionValues;
    }

    public List<String> getZbs() {
        return this.zbs;
    }

    public void setZbs(List<String> zbs) {
        this.zbs = zbs;
    }

    public List<String> getValues() {
        return this.values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}

