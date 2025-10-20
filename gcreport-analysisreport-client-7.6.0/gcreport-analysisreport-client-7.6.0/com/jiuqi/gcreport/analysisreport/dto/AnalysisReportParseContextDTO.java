/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.analysisreport.dto;

import java.util.List;

public class AnalysisReportParseContextDTO {
    private String calendarCode;
    private List<ChooseUnit> chooseUnits;
    private Long curTimeStamp = 0L;
    private String exportType = "word";
    private String key = "";
    private List<Period> periods;
    private String periodTitle;
    private String securityTitle = "";
    private String taskid = "";
    private String title = "";
    private String token;
    private String versionKey = "";
    private Long xDPI = 96L;
    private Long yDPI = 96L;

    public String getCalendarCode() {
        return this.calendarCode;
    }

    public void setCalendarCode(String calendarCode) {
        this.calendarCode = calendarCode;
    }

    public List<ChooseUnit> getChooseUnits() {
        return this.chooseUnits;
    }

    public void setChooseUnits(List<ChooseUnit> chooseUnits) {
        this.chooseUnits = chooseUnits;
    }

    public String getExportType() {
        return this.exportType;
    }

    public void setExportType(String exportType) {
        this.exportType = exportType;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<Period> getPeriods() {
        return this.periods;
    }

    public void setPeriods(List<Period> periods) {
        this.periods = periods;
    }

    public String getPeriodTitle() {
        return this.periodTitle;
    }

    public void setPeriodTitle(String periodTitle) {
        this.periodTitle = periodTitle;
    }

    public String getSecurityTitle() {
        return this.securityTitle;
    }

    public void setSecurityTitle(String securityTitle) {
        this.securityTitle = securityTitle;
    }

    public String getTaskid() {
        return this.taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getVersionKey() {
        return this.versionKey;
    }

    public void setVersionKey(String versionKey) {
        this.versionKey = versionKey;
    }

    public Long getxDPI() {
        return this.xDPI;
    }

    public void setxDPI(Long xDPI) {
        this.xDPI = xDPI;
    }

    public Long getyDPI() {
        return this.yDPI;
    }

    public void setyDPI(Long yDPI) {
        this.yDPI = yDPI;
    }

    public Long getCurTimeStamp() {
        return this.curTimeStamp;
    }

    public void setCurTimeStamp(Long curTimeStamp) {
        this.curTimeStamp = curTimeStamp;
    }

    public static class Period {
        private String calendarCode;
        private String viewKey;

        public String getCalendarCode() {
            return this.calendarCode;
        }

        public void setCalendarCode(String value) {
            this.calendarCode = value;
        }

        public String getViewKey() {
            return this.viewKey;
        }

        public void setViewKey(String value) {
            this.viewKey = value;
        }
    }

    public static class ChooseUnit {
        private String code;
        private String key;
        private String title;
        private String viewKey;

        public String getCode() {
            return this.code;
        }

        public void setCode(String value) {
            this.code = value;
        }

        public String getKey() {
            return this.key;
        }

        public void setKey(String value) {
            this.key = value;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String value) {
            this.title = value;
        }

        public String getViewKey() {
            return this.viewKey;
        }

        public void setViewKey(String value) {
            this.viewKey = value;
        }
    }
}

