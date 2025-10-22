/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.input;

import com.jiuqi.nr.jtable.params.input.CellQueryInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value="RegionFilterInfo", description="\u533a\u57df\u8fc7\u6ee4\u4fe1\u606f")
public class RegionFilterInfo {
    @ApiModelProperty(value="\u533a\u57df\u8fc7\u6ee4\u6761\u4ef6", name="filterFormula")
    private List<String> filterFormula = new ArrayList<String>();
    @ApiModelProperty(value="\u5355\u5143\u683c\u67e5\u8be2\u53c2\u6570", name="cellQuerys")
    private List<CellQueryInfo> cellQuerys = new ArrayList<CellQueryInfo>();
    @ApiModelProperty(value="\u5df2\u9009\u7684\u6307\u6807\u5217\u8868", name="fieldKeys")
    private List<String> fieldKeys = new ArrayList<String>();

    public List<String> getFilterFormula() {
        return this.filterFormula;
    }

    public void setFilterFormula(List<String> filterFormula) {
        this.filterFormula = filterFormula;
    }

    public List<CellQueryInfo> getCellQuerys() {
        return this.cellQuerys;
    }

    public void setCellQuerys(List<CellQueryInfo> cellQuerys) {
        this.cellQuerys = cellQuerys;
    }

    public List<String> getFieldKeys() {
        return this.fieldKeys;
    }

    public void setFieldKeys(List<String> fieldKeys) {
        this.fieldKeys = fieldKeys;
    }
}

