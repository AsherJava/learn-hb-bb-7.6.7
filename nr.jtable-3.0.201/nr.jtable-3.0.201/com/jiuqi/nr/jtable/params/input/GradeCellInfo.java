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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;

@ApiModel(value="GradeCellInfo", description="\u5206\u7ea7\u5355\u5143\u683c\u4fe1\u606f")
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class GradeCellInfo {
    @ApiModelProperty(value="\u5206\u7ea7\u94fe\u63a5", name="zbid")
    private String zbid;
    @ApiModelProperty(value="\u5206\u7ea7\u5b57\u6bb5\u7ea7\u6b21\u7801", name="gradeStruct")
    private String gradeStruct;
    @ApiModelProperty(value="\u9690\u85cf\u672b\u5c3e0", name="trim")
    private boolean trim;
    @ApiModelProperty(value="\u5206\u7ea7\u5b57\u6bb5\u7ea7\u6b21\u5217\u8868", name="levels")
    private ArrayList<Integer> levels = new ArrayList();
    @ApiModelProperty(value="\u6307\u6807\u6c47\u603b\u7c7b\u578b", name="gatherType")
    private int gatherType;

    public String getZbid() {
        return this.zbid;
    }

    public void setZbid(String zbid) {
        this.zbid = zbid;
    }

    public String getGradeStruct() {
        return this.gradeStruct;
    }

    public void setGradeStruct(String gradeStruct) {
        this.gradeStruct = gradeStruct;
    }

    public boolean isTrim() {
        return this.trim;
    }

    public void setTrim(boolean trim) {
        this.trim = trim;
    }

    public ArrayList<Integer> getLevels() {
        return this.levels;
    }

    public void setLevels(ArrayList<Integer> levels) {
        this.levels = levels;
    }

    public int getGatherType() {
        return this.gatherType;
    }

    public void setGatherType(int gatherType) {
        this.gatherType = gatherType;
    }
}

