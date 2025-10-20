/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.workingpaper.vo;

import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryWayItemVO;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTypeOptionVO;
import java.util.List;

public class WorkingPaperQueryWayVO {
    private List<WorkingPaperQueryWayItemVO> ways;
    private List<WorkingPaperTypeOptionVO> dxsTypes;
    private List<WorkingPaperTypeOptionVO> qmsTypes;
    private List<WorkingPaperTypeOptionVO> queryTypes;

    public List<WorkingPaperQueryWayItemVO> getWays() {
        return this.ways;
    }

    public void setWays(List<WorkingPaperQueryWayItemVO> ways) {
        this.ways = ways;
    }

    public List<WorkingPaperTypeOptionVO> getDxsTypes() {
        return this.dxsTypes;
    }

    public void setDxsTypes(List<WorkingPaperTypeOptionVO> dxsTypes) {
        this.dxsTypes = dxsTypes;
    }

    public List<WorkingPaperTypeOptionVO> getQmsTypes() {
        return this.qmsTypes;
    }

    public void setQmsTypes(List<WorkingPaperTypeOptionVO> qmsTypes) {
        this.qmsTypes = qmsTypes;
    }

    public void setQueryTypes(List<WorkingPaperTypeOptionVO> queryTypes) {
        this.queryTypes = queryTypes;
    }

    public List<WorkingPaperTypeOptionVO> getQueryTypes() {
        return this.queryTypes;
    }
}

