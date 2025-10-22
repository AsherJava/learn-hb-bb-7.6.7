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

@ApiModel(value="DataFillEntityAnslysisItemInfo", description="\u67e5\u8be2\u5355\u5143\u683c\u53ef\u9009\u503c\u4fe1\u606f")
@JsonIgnoreProperties(ignoreUnknown=true)
public class DataFillEntityAnslysisItemInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="\u7ef4\u5ea6\u5217\u8868", name="dimensionValues", required=true)
    private List<DFDimensionValue> dimensionValues;
    @ApiModelProperty(value="\u641c\u7d22\u5b57\u7b26", name="search")
    private String search;

    public List<DFDimensionValue> getDimensionValues() {
        return this.dimensionValues;
    }

    public void setDimensionValues(List<DFDimensionValue> dimensionValues) {
        this.dimensionValues = dimensionValues;
    }

    public String getSearch() {
        return this.search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}

