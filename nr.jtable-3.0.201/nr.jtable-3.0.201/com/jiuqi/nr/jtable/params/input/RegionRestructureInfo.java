/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.input;

import com.jiuqi.nr.jtable.params.input.RegionGradeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="RegionRestructureInfo", description="\u533a\u57df\u6570\u636e\u91cd\u6784\u6761\u4ef6")
public class RegionRestructureInfo {
    @ApiModelProperty(value="\u4e0d\u9700\u8981\u6c47\u603b\u548c\u5206\u7ea7\u5408\u8ba1", name="noSumData")
    private boolean noSumData = false;
    @ApiModelProperty(value="\u679a\u4e3e\u81ea\u52a8\u5c55\u5f00\u6307\u6807", name="expandField")
    private String expandField;
    @ApiModelProperty(value="\u533a\u57df\u5206\u7ea7\u5408\u8ba1\u53c2\u6570", name="grade")
    private RegionGradeInfo grade;
    @ApiModelProperty(value="\u662f\u5426\u5355\u4f4d\u81ea\u52a8\u6c47\u603b", name="unitAutoSum")
    private boolean unitAutoSum = false;
    @ApiModelProperty(value="\u662f\u5426\u65f6\u671f\u81ea\u52a8\u6c47\u603b", name="periodAutoSum")
    private boolean periodAutoSum = false;
    @ApiModelProperty(value="\u533a\u57df\u6570\u636e\u5b9a\u4f4dID", name="dataID")
    private String dataID;
    @ApiModelProperty(value="\u533a\u57df\u6570\u636e\u6392\u5e8fOrder", name="floatOrder")
    private String floatOrder;
    @ApiModelProperty(value="\u533a\u57df\u6570\u636e\u504f\u79fb\u91cf", name="offset")
    private int offset;
    @ApiModelProperty(value="\u662f\u5426\u683c\u5f0f\u5316\u67e5\u8be2\u7ed3\u679c\u4e2d\u57fa\u7840\u6570\u636e\u7684\u503c", name="offset")
    private boolean formatBaseDataValue = false;
    @ApiModelProperty(value="\u662f\u5426\u81ea\u5b9a\u4e49\u5206\u7ea7\u5408\u8ba1", name="isCustomGrade")
    private boolean customGrade = false;

    public boolean isNoSumData() {
        return this.noSumData;
    }

    public void setNoSumData(boolean noSumData) {
        this.noSumData = noSumData;
    }

    public String getExpandField() {
        return this.expandField;
    }

    public void setExpandField(String expandField) {
        this.expandField = expandField;
    }

    public RegionGradeInfo getGrade() {
        return this.grade;
    }

    public void setGrade(RegionGradeInfo grade) {
        this.grade = grade;
    }

    public boolean isUnitAutoSum() {
        return this.unitAutoSum;
    }

    public void setUnitAutoSum(boolean unitAutoSum) {
        this.unitAutoSum = unitAutoSum;
    }

    public boolean isPeriodAutoSum() {
        return this.periodAutoSum;
    }

    public void setPeriodAutoSum(boolean periodAutoSum) {
        this.periodAutoSum = periodAutoSum;
    }

    public String getDataID() {
        return this.dataID;
    }

    public void setDataID(String dataID) {
        this.dataID = dataID;
    }

    public String getFloatOrder() {
        return this.floatOrder;
    }

    public void setFloatOrder(String floatOrder) {
        this.floatOrder = floatOrder;
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isFormatBaseDataValue() {
        return this.formatBaseDataValue;
    }

    public void setFormatBaseDataValue(boolean formatBaseDataValue) {
        this.formatBaseDataValue = formatBaseDataValue;
    }

    public boolean isCustomGrade() {
        return this.customGrade;
    }

    public void setCustomGrade(boolean customGrade) {
        this.customGrade = customGrade;
    }
}

