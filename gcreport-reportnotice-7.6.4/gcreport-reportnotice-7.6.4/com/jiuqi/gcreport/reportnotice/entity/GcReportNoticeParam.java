/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodWrapper
 */
package com.jiuqi.gcreport.reportnotice.entity;

import com.jiuqi.np.period.PeriodWrapper;
import java.util.List;
import java.util.Map;

public class GcReportNoticeParam {
    private String periodType;
    private List<String> ccInfos;
    private List<String> jsInfos;
    private String departureAction;
    private String textarea;
    private String schemeId;
    private String title;
    private List<String> orgCodeList;
    private String taskId;
    private String fromKeyStrs;
    private List<Map<String, String>> fromKeyListMap;
    private PeriodWrapper periodWrapper;
    private String sendType;
    private String shortMessageTitle;
    private String shortMessageTextarea;
    private String orgType;

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getSendType() {
        return this.sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public String getShortMessageTitle() {
        return this.shortMessageTitle;
    }

    public void setShortMessageTitle(String shortMessageTitle) {
        this.shortMessageTitle = shortMessageTitle;
    }

    public String getShortMessageTextarea() {
        return this.shortMessageTextarea;
    }

    public void setShortMessageTextarea(String shortMessageTextarea) {
        this.shortMessageTextarea = shortMessageTextarea;
    }

    public PeriodWrapper getPeriodWrapper() {
        return this.periodWrapper;
    }

    public void setPeriodWrapper(PeriodWrapper periodWrapper) {
        this.periodWrapper = periodWrapper;
    }

    public String getFromKeyStrs() {
        return this.fromKeyStrs;
    }

    public void setFromKeyStrs(String fromKeyStrs) {
        this.fromKeyStrs = fromKeyStrs;
    }

    public List<Map<String, String>> getFromKeyListMap() {
        return this.fromKeyListMap;
    }

    public void setFromKeyListMap(List<Map<String, String>> fromKeyListMap) {
        this.fromKeyListMap = fromKeyListMap;
    }

    public List<String> getJsInfos() {
        return this.jsInfos;
    }

    public void setJsInfos(List<String> jsInfos) {
        this.jsInfos = jsInfos;
    }

    public String getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public List<String> getCcInfos() {
        return this.ccInfos;
    }

    public void setCcInfos(List<String> ccInfos) {
        this.ccInfos = ccInfos;
    }

    public String getDepartureAction() {
        return this.departureAction;
    }

    public void setDepartureAction(String departureAction) {
        this.departureAction = departureAction;
    }

    public String getTextarea() {
        return this.textarea;
    }

    public void setTextarea(String textarea) {
        this.textarea = textarea;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getOrgCodeList() {
        return this.orgCodeList;
    }

    public void setOrgCodeList(List<String> orgCodeList) {
        this.orgCodeList = orgCodeList;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}

