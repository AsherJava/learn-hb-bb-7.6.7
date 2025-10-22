/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.archive.entity;

import java.util.List;
import java.util.Map;

public class ILRunnerEntity {
    private String taskId;
    private String schemeId;
    private List<String> orgCodeList;
    private String departureAction;
    private List<Map<String, String>> fromKeyListMap;
    private String libraryDate;
    private String adjustCode;
    private int offset;
    private String programmeId;
    private List<String> userList;
    private String orgType;

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public List<String> getOrgCodeList() {
        return this.orgCodeList;
    }

    public void setOrgCodeList(List<String> orgCodeList) {
        this.orgCodeList = orgCodeList;
    }

    public String getDepartureAction() {
        return this.departureAction;
    }

    public void setDepartureAction(String departureAction) {
        this.departureAction = departureAction;
    }

    public List<Map<String, String>> getFromKeyListMap() {
        return this.fromKeyListMap;
    }

    public void setFromKeyListMap(List<Map<String, String>> fromKeyListMap) {
        this.fromKeyListMap = fromKeyListMap;
    }

    public String getLibraryDate() {
        return this.libraryDate;
    }

    public void setLibraryDate(String libraryDate) {
        this.libraryDate = libraryDate;
    }

    public String getAdjustCode() {
        return this.adjustCode;
    }

    public void setAdjustCode(String adjustCode) {
        this.adjustCode = adjustCode;
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getProgrammeId() {
        return this.programmeId;
    }

    public void setProgrammeId(String programmeId) {
        this.programmeId = programmeId;
    }

    public List<String> getUserList() {
        return this.userList;
    }

    public void setUserList(List<String> userList) {
        this.userList = userList;
    }

    public String toString() {
        return "ILRunnerEntity{taskId='" + this.taskId + '\'' + ", schemeId='" + this.schemeId + '\'' + ", orgCodeList=" + this.orgCodeList + ", departureAction='" + this.departureAction + '\'' + ", fromKeyListMap=" + this.fromKeyListMap + ", libraryDate='" + this.libraryDate + '\'' + ", adjustCode='" + this.adjustCode + '\'' + ", offset=" + this.offset + ", programmeId='" + this.programmeId + '\'' + ", orgType='" + this.orgType + '\'' + '}';
    }
}

