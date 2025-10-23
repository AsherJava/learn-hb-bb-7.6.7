/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.zbquery.rest.vo.QueryConfigVO
 */
package com.jiuqi.nr.singlequeryimport.bean;

import com.jiuqi.nr.zbquery.rest.vo.QueryConfigVO;
import java.util.List;
import java.util.Map;

public class QueryConfigInfo
extends QueryConfigVO {
    private String ModelId;
    private Integer custom;
    private Boolean totalLine = false;
    private Boolean columnNumber = false;
    private Integer lockRow = 0;
    private Integer lockColumn = 0;
    private Boolean add = false;
    private Boolean KMBM = false;
    private String divideValue;
    private Boolean zeroEmpty = true;
    private List<Map<String, String>> ordersList;
    private Boolean treeDisPlay = false;
    private Boolean hasName;
    private List<String> indexSelectList;
    private Boolean isCheckModel = false;
    private Integer selectNode = -1;

    public String getDivideValue() {
        return this.divideValue;
    }

    public void setDivideValue(String divideValue) {
        this.divideValue = divideValue;
    }

    public List<String> getIndexSelectList() {
        return this.indexSelectList;
    }

    public void setIndexSelectList(List<String> indexSelectList) {
        this.indexSelectList = indexSelectList;
    }

    public Boolean getTotalLine() {
        return this.totalLine;
    }

    public void setTotalLine(Boolean totalLine) {
        this.totalLine = totalLine;
    }

    public Integer getLockRow() {
        return this.lockRow;
    }

    public void setLockRow(Integer lockRow) {
        this.lockRow = lockRow;
    }

    public Integer getLockColumn() {
        return this.lockColumn;
    }

    public void setLockColumn(Integer lockColumn) {
        this.lockColumn = lockColumn;
    }

    public Integer getCustom() {
        return this.custom;
    }

    public void setCustom(Integer custom) {
        this.custom = custom;
    }

    public String getModelId() {
        return this.ModelId;
    }

    public void setModelId(String modelId) {
        this.ModelId = modelId;
    }

    public Boolean getColumnNumber() {
        return this.columnNumber;
    }

    public void setColumnNumber(Boolean columnNumber) {
        this.columnNumber = columnNumber;
    }

    public Boolean getAdd() {
        return this.add;
    }

    public void setAdd(Boolean add) {
        this.add = add;
    }

    public Boolean getKMBM() {
        return this.KMBM;
    }

    public void setKMBM(Boolean KMBM) {
        this.KMBM = KMBM;
    }

    public List<Map<String, String>> getOrdersList() {
        return this.ordersList;
    }

    public void setOrdersList(List<Map<String, String>> ordersList) {
        this.ordersList = ordersList;
    }

    public Boolean getZeroEmpty() {
        return this.zeroEmpty;
    }

    public void setZeroEmpty(Boolean zeroEmpty) {
        this.zeroEmpty = zeroEmpty;
    }

    public Boolean getCheckModel() {
        return this.isCheckModel;
    }

    public void setCheckModel(Boolean checkModel) {
        this.isCheckModel = checkModel;
    }

    public Boolean getHasName() {
        return this.hasName;
    }

    public void setHasName(Boolean hasName) {
        this.hasName = hasName;
    }

    public Integer getSelectNode() {
        return this.selectNode;
    }

    public void setSelectNode(Integer selectNode) {
        this.selectNode = selectNode;
    }

    public Boolean getTreeDisPlay() {
        return this.treeDisPlay;
    }

    public void setTreeDisPlay(Boolean treeDisPlay) {
        this.treeDisPlay = treeDisPlay;
    }
}

