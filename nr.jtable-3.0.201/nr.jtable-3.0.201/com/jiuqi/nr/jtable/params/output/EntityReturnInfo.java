/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.output;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.jtable.params.output.EntityData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ApiModel(value="EntityReturnInfo", description="\u4e3b\u4f53\u6570\u636e\u67e5\u8be2\u7ed3\u679c")
public class EntityReturnInfo {
    @ApiModelProperty(value="\u67e5\u8be2\u7ed3\u679c\u4fe1\u606f", name="message")
    private String message;
    @ApiModelProperty(value="\u67e5\u8be2\u7ed3\u679c\u5176\u4ed6\u5c5e\u6027\u6307\u6807\u5217\u8868", name="cells")
    private List<String> cells = new ArrayList<String>();
    @ApiModelProperty(value="\u4e3b\u4f53\u6570\u636e\u679a\u4e3e\u6761\u76ee\u5217\u8868", name="entitys")
    private List<EntityData> entitys = new ArrayList<EntityData>();
    @ApiModelProperty(value="\u4e3b\u4f53\u6570\u636e\u679a\u4e3e\u7ef4\u5ea6", name="dimensionSet")
    private Map<String, DimensionValue> dimensionSet;
    @ApiModelProperty(value="\u8131\u654f\u5b57\u6bb5\u5217\u8868", name="entitys")
    private List<String> dataMaskFields = new ArrayList<String>();

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getCells() {
        return this.cells;
    }

    public void setCells(List<String> cells) {
        this.cells = cells;
    }

    public List<EntityData> getEntitys() {
        return this.entitys;
    }

    public void setEntitys(List<EntityData> entitys) {
        this.entitys = entitys;
    }

    public Map<String, DimensionValue> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, DimensionValue> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public List<String> getDataMaskFields() {
        return this.dataMaskFields;
    }

    public void setDataMaskFields(List<String> dataMaskFields) {
        this.dataMaskFields = dataMaskFields;
    }
}

