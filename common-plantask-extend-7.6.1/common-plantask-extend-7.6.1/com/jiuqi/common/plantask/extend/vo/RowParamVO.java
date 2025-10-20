/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.plantask.extend.vo;

import com.jiuqi.common.plantask.extend.vo.ColParamVO;
import java.util.ArrayList;
import java.util.List;

public class RowParamVO {
    private Integer totalcolSpan;
    private Integer rowIndex;
    private List<ColParamVO> colParams;

    public RowParamVO(Integer rowIndex) {
        this.rowIndex = rowIndex;
        this.totalcolSpan = 0;
        this.colParams = new ArrayList<ColParamVO>();
    }

    public Integer getTotalcolSpan() {
        return this.totalcolSpan;
    }

    public void setTotalcolSpan(Integer totalcolSpan) {
        this.totalcolSpan = totalcolSpan;
    }

    public List<ColParamVO> getColParams() {
        return this.colParams;
    }

    public void setColParams(List<ColParamVO> colParams) {
        this.colParams = colParams;
    }

    public void addColParams(ColParamVO colParamVO) {
        this.colParams.add(colParamVO);
        RowParamVO rowParamVO = this;
        rowParamVO.totalcolSpan = rowParamVO.totalcolSpan + colParamVO.getColSpan();
    }

    public String toString() {
        return "RowParamVO{totalcolSpan=" + this.totalcolSpan + ", colParams=" + this.colParams + '}';
    }

    public Integer getRowIndex() {
        return this.rowIndex;
    }

    public void setRowIndex(Integer rowIndex) {
        this.rowIndex = rowIndex;
    }
}

