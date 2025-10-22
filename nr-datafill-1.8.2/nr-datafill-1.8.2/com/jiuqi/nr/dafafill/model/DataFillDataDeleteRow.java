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

@ApiModel(value="DataFillDataDeleteRow", description="\u6570\u636e\u5220\u9664\u884c")
@JsonIgnoreProperties(ignoreUnknown=true)
public class DataFillDataDeleteRow
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="\u7ef4\u5ea6\u5217\u8868", name="dimensionValues", required=true)
    private List<DFDimensionValue> dimensionValues;
    @ApiModelProperty(value="\u6d6e\u52a8\u884c\u4e3b\u952e\u7684\u5217\u8868\u503c", name="values", required=false)
    private List<String> values;

    public List<DFDimensionValue> getDimensionValues() {
        return this.dimensionValues;
    }

    public void setDimensionValues(List<DFDimensionValue> dimensionValues) {
        this.dimensionValues = dimensionValues;
    }

    public List<String> getValues() {
        return this.values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}

