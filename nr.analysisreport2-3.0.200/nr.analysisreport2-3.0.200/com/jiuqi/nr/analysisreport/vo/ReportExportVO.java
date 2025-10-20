/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.analysisreport.vo;

import com.jiuqi.nr.analysisreport.vo.ReportBaseVO;
import com.jiuqi.nr.analysisreport.vo.ReportGeneratorVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReportExportVO
extends ReportGeneratorVO
implements Serializable,
Cloneable {
    private String title;
    private String taskid;
    private String exportType;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTaskid() {
        return this.taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getExportType() {
        return this.exportType;
    }

    public void setExportType(String exportType) {
        this.exportType = exportType;
    }

    public ReportExportVO clone() {
        ReportExportVO reportExportVO = null;
        try {
            reportExportVO = (ReportExportVO)super.clone();
        }
        catch (CloneNotSupportedException e) {
            reportExportVO = new ReportExportVO();
        }
        List<ReportBaseVO.UnitDim> originalChooseUnits = reportExportVO.getChooseUnits();
        ArrayList<ReportBaseVO.UnitDim> chooseUnits = new ArrayList<ReportBaseVO.UnitDim>();
        for (ReportBaseVO.UnitDim orgionChooseUnit : originalChooseUnits) {
            chooseUnits.add(orgionChooseUnit.clone());
        }
        reportExportVO.setChooseUnits(chooseUnits);
        return reportExportVO;
    }
}

