/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.financialcubes.dto;

import java.util.LinkedHashMap;
import java.util.List;

public class FinancialCubesResult {
    private LinkedHashMap<String, Integer> columns;
    private List<Object[]> rowDatas;
    private String mdCode;
    private String dataTime;
    private Integer batchNum;

    public LinkedHashMap<String, Integer> getColumns() {
        return this.columns;
    }

    public void setColumns(LinkedHashMap<String, Integer> columns) {
        this.columns = columns;
    }

    public List<Object[]> getRowDatas() {
        return this.rowDatas;
    }

    public void setRowDatas(List<Object[]> rowDatas) {
        this.rowDatas = rowDatas;
    }

    public String toString() {
        return "FinancialCubesDimDTO{floatColumns=" + this.columns + ", rowDatas=" + this.rowDatas + '}';
    }

    public String getMdCode() {
        return this.mdCode;
    }

    public void setMdCode(String mdCode) {
        this.mdCode = mdCode;
    }

    public String getDataTime() {
        return this.dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public Integer getBatchNum() {
        return this.batchNum;
    }

    public void setBatchNum(Integer batchNum) {
        this.batchNum = batchNum;
    }
}

