/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.onekeymerge.vo;

import com.jiuqi.gcreport.onekeymerge.vo.GcBaseTaskStateVO;
import java.util.ArrayList;
import java.util.List;

public class GcReportPickVO
extends GcBaseTaskStateVO {
    private String orgName;
    private String pickWay;
    private String dataReview;
    private String dataReport;
    private String journalAdjust;
    private String useTime;
    private List<GcReportPickVO> children;
    private String parentId;

    public String getOrgName() {
        return this.orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getPickWay() {
        return this.pickWay;
    }

    public void setPickWay(String pickWay) {
        this.pickWay = pickWay;
    }

    public String getDataReview() {
        return this.dataReview;
    }

    public void setDataReview(String dataReview) {
        this.dataReview = dataReview;
    }

    public String getDataReport() {
        return this.dataReport;
    }

    public void setDataReport(String dataReport) {
        this.dataReport = dataReport;
    }

    public String getJournalAdjust() {
        return this.journalAdjust;
    }

    public void setJournalAdjust(String journalAdjust) {
        this.journalAdjust = journalAdjust;
    }

    public List<GcReportPickVO> getChildren() {
        if (this.children == null) {
            this.children = new ArrayList<GcReportPickVO>();
        }
        return this.children;
    }

    public void setChildren(List<GcReportPickVO> children) {
        this.children = children;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getUseTime() {
        return this.useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }
}

