/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.data.logic.facade.param.output;

import com.jiuqi.nr.data.logic.facade.param.output.DesCheckData;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;

public class DesCheckResult
implements Serializable {
    private static final long serialVersionUID = 7347383315224726059L;
    @ApiModelProperty(value="\u68c0\u67e5\u7684\u5b57\u7b26\u6700\u5c11\u4e2a\u6570", name="checkCharNum")
    private int checkCharMinNum;
    @ApiModelProperty(value="\u68c0\u67e5\u7684\u5b57\u7b26\u6700\u591a\u4e2a\u6570", name="checkCharNum")
    private int checkCharMaxNum;
    @ApiModelProperty(value="\u9519\u8bef\u7ed3\u679c\u5217\u8868", name="checkReturnInfos")
    private List<DesCheckData> desCheckData;

    public int getCheckCharMinNum() {
        return this.checkCharMinNum;
    }

    public void setCheckCharMinNum(int checkCharMinNum) {
        this.checkCharMinNum = checkCharMinNum;
    }

    public int getCheckCharMaxNum() {
        return this.checkCharMaxNum;
    }

    public void setCheckCharMaxNum(int checkCharMaxNum) {
        this.checkCharMaxNum = checkCharMaxNum;
    }

    public List<DesCheckData> getDesCheckData() {
        return this.desCheckData;
    }

    public void setDesCheckData(List<DesCheckData> desCheckData) {
        this.desCheckData = desCheckData;
    }
}

