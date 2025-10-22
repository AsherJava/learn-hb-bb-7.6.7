/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.data.excel.param;

import com.jiuqi.nr.data.excel.param.FilterCondition;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value="CellQueryInfo", description="\u5355\u5143\u683c\u67e5\u8be2\u53c2\u6570")
public class CellQueryInfo {
    @ApiModelProperty(value="\u94fe\u63a5key", name="cellKey", required=true)
    private String cellKey;
    @ApiModelProperty(value="\u6392\u5e8f\u65b9\u5f0f", name="sort")
    private String sort;
    @ApiModelProperty(value="\u6570\u636e\u8fc7\u6ee4\u6761\u4ef6", name="filter")
    private String filterFormula;
    @ApiModelProperty(value="\u6570\u636e\u8fc7\u6ee4\u5668\u5217\u8868", name="opList")
    private List<FilterCondition> opList = new ArrayList<FilterCondition>();
    @ApiModelProperty(value="\u8fc7\u6ee4\u5668\u5173\u7cfb\uff08and \uff0c or\uff09", name="attendedMode")
    private String attendedMode;
    @ApiModelProperty(value="\u5feb\u6377\u952e\uff08\u5927\u4e8e\u5e73\u5747\u503c:moreThanEverage;\u5c0f\u4e8e\u5e73\u5747\u503c:lessThanEverage;\u524d\u5341\u6761:topTen\uff09", name="shortcuts")
    private String shortcuts;
    @ApiModelProperty(value="\u503c\u5217\u8868", name="inList")
    private List<String> inList = new ArrayList<String>();
    @ApiModelProperty(value="\u6392\u9664\u503c\u5217\u8868,\u82e5\u6709\u503c\u5219inList\u65e0\u6548", name="outList")
    private List<String> outList = new ArrayList<String>();
    private final List<String> excelInList = new ArrayList<String>();

    public String getCellKey() {
        return this.cellKey;
    }

    public void setCellKey(String cellKey) {
        this.cellKey = cellKey;
    }

    public String getSort() {
        return this.sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getFilterFormula() {
        return this.filterFormula;
    }

    public void setFilterFormula(String filterFormula) {
        this.filterFormula = filterFormula;
    }

    public List<String> getInList() {
        return this.inList;
    }

    public void setInList(List<String> inList) {
        this.inList = inList;
    }

    public String getAttendedMode() {
        return this.attendedMode;
    }

    public void setAttendedMode(String attendedMode) {
        this.attendedMode = attendedMode;
    }

    public String getShortcuts() {
        return this.shortcuts;
    }

    public void setShortcuts(String shortcuts) {
        this.shortcuts = shortcuts;
    }

    public List<FilterCondition> getOpList() {
        return this.opList;
    }

    public void setOpList(List<FilterCondition> opList) {
        this.opList = opList;
    }

    public List<String> getOutList() {
        return this.outList;
    }

    public void setOutList(List<String> outList) {
        this.outList = outList;
    }

    public List<String> getExcelInList() {
        return this.excelInList;
    }
}

