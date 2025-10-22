/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.formsetting.vo;

public class DataRegionSettingVO {
    private String fixedRegionId;
    private String floatRegionId;
    private Integer beginRowNum;
    private Boolean showHead;
    private Boolean showColumnOrderNum;
    private Boolean showSum;

    public int getHeadRowIndex() {
        return this.showHead == Boolean.TRUE ? this.beginRowNum : -1;
    }

    public int getColumnOrderRowIndex() {
        if (this.showColumnOrderNum != Boolean.TRUE) {
            return -1;
        }
        int beginNum = this.beginRowNum;
        return beginNum += this.showHead != false ? 1 : 0;
    }

    public int getSumRowIndex() {
        if (this.showSum != Boolean.TRUE) {
            return -1;
        }
        int beginNum = this.beginRowNum;
        beginNum += this.showHead != false ? 1 : 0;
        return beginNum += this.showColumnOrderNum != false ? 1 : 0;
    }

    public int getFloatBeginRowNum() {
        int beginNum = this.beginRowNum;
        beginNum += this.showHead != false ? 1 : 0;
        beginNum += this.showColumnOrderNum != false ? 1 : 0;
        return beginNum += this.showSum != false ? 1 : 0;
    }

    public int getRowNums() {
        int rowNums = 1;
        rowNums += this.showHead != false ? 1 : 0;
        rowNums += this.showColumnOrderNum != false ? 1 : 0;
        return rowNums += this.showSum != false ? 1 : 0;
    }

    public String getFixedRegionId() {
        return this.fixedRegionId;
    }

    public void setFixedRegionId(String fixedRegionId) {
        this.fixedRegionId = fixedRegionId;
    }

    public String getFloatRegionId() {
        return this.floatRegionId;
    }

    public void setFloatRegionId(String floatRegionId) {
        this.floatRegionId = floatRegionId;
    }

    public Integer getBeginRowNum() {
        return this.beginRowNum;
    }

    public void setBeginRowNum(Integer beginRowNum) {
        this.beginRowNum = beginRowNum;
    }

    public Boolean getShowHead() {
        return this.showHead;
    }

    public void setShowHead(Boolean showHead) {
        this.showHead = showHead;
    }

    public Boolean getShowColumnOrderNum() {
        return this.showColumnOrderNum;
    }

    public void setShowColumnOrderNum(Boolean showColumnOrderNum) {
        this.showColumnOrderNum = showColumnOrderNum;
    }

    public Boolean getShowSum() {
        return this.showSum;
    }

    public void setShowSum(Boolean showSum) {
        this.showSum = showSum;
    }

    public String toString() {
        return "DataRegionDesign [fixedRegionId=" + this.fixedRegionId + ", floatRegionId=" + this.floatRegionId + ", beginRowNum=" + this.beginRowNum + ", showHead=" + this.showHead + ", showColumOrderNum=" + this.showColumnOrderNum + ", showSum=" + this.showSum + "]";
    }
}

