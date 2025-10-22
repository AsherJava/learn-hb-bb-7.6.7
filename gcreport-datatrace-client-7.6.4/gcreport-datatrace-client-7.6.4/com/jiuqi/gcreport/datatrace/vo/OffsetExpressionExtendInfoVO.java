/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO
 */
package com.jiuqi.gcreport.datatrace.vo;

import com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO;
import java.util.List;
import java.util.Map;

public class OffsetExpressionExtendInfoVO {
    private String calcTitle;
    private String calcProcessDes;
    private List<DesignFieldDefineVO> tableColumns;
    private List<Object> tableDatas;
    private Map otherExtendInfo;

    public String getCalcTitle() {
        return this.calcTitle;
    }

    public void setCalcTitle(String calcTitle) {
        this.calcTitle = calcTitle;
    }

    public String getCalcProcessDes() {
        return this.calcProcessDes;
    }

    public void setCalcProcessDes(String calcProcessDes) {
        this.calcProcessDes = calcProcessDes;
    }

    public List<DesignFieldDefineVO> getTableColumns() {
        return this.tableColumns;
    }

    public void setTableColumns(List<DesignFieldDefineVO> tableColumns) {
        this.tableColumns = tableColumns;
    }

    public List<Object> getTableDatas() {
        return this.tableDatas;
    }

    public void setTableDatas(List<Object> tableDatas) {
        this.tableDatas = tableDatas;
    }

    public Map getOtherExtendInfo() {
        return this.otherExtendInfo;
    }

    public void setOtherExtendInfo(Map otherExtendInfo) {
        this.otherExtendInfo = otherExtendInfo;
    }

    public String toString() {
        return "OffsetExpressionExtendInfoVO{calcTitle='" + this.calcTitle + '\'' + ", calcProcessDes='" + this.calcProcessDes + '\'' + ", tableColumns=" + this.tableColumns + ", tableDatas=" + this.tableDatas + ", otherExtendInfo=" + this.otherExtendInfo + '}';
    }
}

