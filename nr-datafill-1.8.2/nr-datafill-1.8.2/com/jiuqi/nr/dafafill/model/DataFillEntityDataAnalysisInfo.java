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
import com.jiuqi.nr.dafafill.model.DataFillEntityAnslysisItemInfo;
import com.jiuqi.nr.dafafill.model.DataFillModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;

@ApiModel(value="DataFillEntityDataAnalysisInfo", description="\u67e5\u8be2\u5355\u5143\u683c\u53ef\u9009\u503c\u4fe1\u606f")
@JsonIgnoreProperties(ignoreUnknown=true)
public class DataFillEntityDataAnalysisInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="\u6a21\u578b", name="model", required=false)
    public DataFillModel model;
    @ApiModelProperty(value="\u5168\u6807\u8bc6", name="fullCode", required=true)
    private String fullCode;
    @ApiModelProperty(value="\u67e5\u8be2\u9879", name="items", required=true)
    private List<DataFillEntityAnslysisItemInfo> items;

    public DataFillModel getModel() {
        return this.model;
    }

    public void setModel(DataFillModel model) {
        this.model = model;
    }

    public String getFullCode() {
        return this.fullCode;
    }

    public void setFullCode(String fullCode) {
        this.fullCode = fullCode;
    }

    public List<DataFillEntityAnslysisItemInfo> getItems() {
        return this.items;
    }

    public void setItems(List<DataFillEntityAnslysisItemInfo> items) {
        this.items = items;
    }
}

