/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.input;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.nr.jtable.params.input.GradeCellInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value="RegionGradeInfo", description="\u533a\u57df\u5206\u7ea7\u5408\u8ba1")
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class RegionGradeInfo {
    @ApiModelProperty(value="\u5206\u7ea7\u5408\u8ba1\u662f\u5426\u67e5\u8be2\u660e\u7ec6\u6570\u636e", name="detail")
    private boolean detail;
    @ApiModelProperty(value="\u5206\u7ea7\u5408\u8ba1\u662f\u5426\u67e5\u8be2\u6c47\u603b\u6570\u636e", name="sum")
    private boolean sum;
    @ApiModelProperty(value="\u5206\u7ea7\u5408\u8ba1\u6570\u636e\u662f\u5426\u6298\u53e0", name="fold")
    private boolean fold;
    @ApiModelProperty(value="\u662f\u5426\u9690\u85cf\u5355\u4e00\u660e\u7ec6\u884c", name="hidenRow")
    private boolean hidenRow = false;
    @ApiModelProperty(value="\u5206\u7ea7\u5408\u8ba1\u6570\u636e\u6298\u53e0\u4f4d\u7f6e", name="locate")
    private int locate;
    @ApiModelProperty(value="\u5206\u7ea7\u5408\u8ba1\u5b57\u6bb5\u5217\u8868", name="gradeCells")
    private List<GradeCellInfo> gradeCells = new ArrayList<GradeCellInfo>();
    @ApiModelProperty(value="\u591a\u5b57\u6bb5\u8054\u5408\u5206\u7ea7\u5408\u8ba1\u8bbe\u7f6e", name="levels")
    private ArrayList<Integer> levels = new ArrayList();
    @ApiModelProperty(value="\u5206\u7ea7\u5408\u8ba1\u65f6\u671f\u6c47\u603b", name="period")
    private List<Integer> period = new ArrayList<Integer>();
    @ApiModelProperty(value="\u5206\u7ea7\u5408\u8ba1\u7ea7\u6b21\u7c7b\u578b", name="levelSetType")
    private int levelSetType;

    public boolean isDetail() {
        return this.detail;
    }

    public void setDetail(boolean detail) {
        this.detail = detail;
    }

    public boolean isSum() {
        return this.sum;
    }

    public void setSum(boolean sum) {
        this.sum = sum;
    }

    public boolean isFold() {
        return this.fold;
    }

    public void setFold(boolean fold) {
        this.fold = fold;
    }

    public boolean isHidenRow() {
        return this.hidenRow;
    }

    public void setHidenRow(boolean hidenRow) {
        this.hidenRow = hidenRow;
    }

    public int getLocate() {
        return this.locate;
    }

    public void setLocate(int locate) {
        this.locate = locate;
    }

    public List<GradeCellInfo> getGradeCells() {
        return this.gradeCells;
    }

    public void setGradeCells(List<GradeCellInfo> gradeCells) {
        this.gradeCells = gradeCells;
    }

    public ArrayList<Integer> getLevels() {
        return this.levels;
    }

    public void setLevels(ArrayList<Integer> levels) {
        this.levels = levels;
    }

    public List<Integer> getPeriod() {
        return this.period;
    }

    public void setPeriod(List<Integer> period) {
        this.period = period;
    }

    public int getLevelSetType() {
        return this.levelSetType;
    }

    public void setLevelSetType(int levelSetType) {
        this.levelSetType = levelSetType;
    }
}

