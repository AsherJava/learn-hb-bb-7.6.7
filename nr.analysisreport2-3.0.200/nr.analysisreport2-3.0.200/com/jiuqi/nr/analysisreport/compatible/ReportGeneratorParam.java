/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.analysisreport.compatible;

import java.util.List;

@Deprecated
public class ReportGeneratorParam {
    private String calendarCode;
    private List<UnitDim> chooseUnits;
    private String curTimeStamp;
    private String key;
    private List<PeriodDim> periods;
    private String securityTitle;
    private String versionKey;
    private String arcKey;
    private String contents;
    private String QCY_PROJECTID;
    private Boolean isScientific;
    private int xDPI = 96;
    private int yDPI = 96;

    public String getArcKey() {
        return this.arcKey;
    }

    public void setArcKey(String arcKey) {
        this.arcKey = arcKey;
    }

    public String getCalendarCode() {
        return this.calendarCode;
    }

    public void setCalendarCode(String calendarCode) {
        this.calendarCode = calendarCode;
    }

    public List<UnitDim> getChooseUnits() {
        return this.chooseUnits;
    }

    public void setChooseUnits(List<UnitDim> chooseUnits) {
        this.chooseUnits = chooseUnits;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<PeriodDim> getPeriods() {
        return this.periods;
    }

    public void setPeriods(List<PeriodDim> periods) {
        this.periods = periods;
    }

    public String getSecurityTitle() {
        return this.securityTitle;
    }

    public void setSecurityTitle(String securityTitle) {
        this.securityTitle = securityTitle;
    }

    public String getVersionKey() {
        return this.versionKey;
    }

    public void setVersionKey(String versionKey) {
        this.versionKey = versionKey;
    }

    public int getxDPI() {
        return this.xDPI;
    }

    public void setxDPI(int xDPI) {
        this.xDPI = xDPI;
    }

    public int getyDPI() {
        return this.yDPI;
    }

    public void setyDPI(int yDPI) {
        this.yDPI = yDPI;
    }

    public String getContents() {
        return this.contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getCurTimeStamp() {
        return this.curTimeStamp;
    }

    public void setCurTimeStamp(String curTimeStamp) {
        this.curTimeStamp = curTimeStamp;
    }

    public String getQCY_PROJECTID() {
        return this.QCY_PROJECTID;
    }

    public void setQCY_PROJECTID(String QCY_PROJECTID) {
        this.QCY_PROJECTID = QCY_PROJECTID;
    }

    public Boolean getScientific() {
        return this.isScientific;
    }

    public void setScientific(Boolean scientific) {
        this.isScientific = scientific;
    }

    public static class PeriodDim {
        private String calendarCode;
        private String viewKey;

        public PeriodDim() {
        }

        public PeriodDim(String calendarCode, String viewKey) {
            this.calendarCode = calendarCode;
            this.viewKey = viewKey;
        }

        public String getCalendarCode() {
            return this.calendarCode;
        }

        public void setCalendarCode(String calendarCode) {
            this.calendarCode = calendarCode;
        }

        public String getViewKey() {
            return this.viewKey;
        }

        public void setViewKey(String viewKey) {
            this.viewKey = viewKey;
        }
    }

    public static class UnitDim {
        private String code;
        private String key;
        private String title;
        private String viewKey;

        public UnitDim() {
        }

        public UnitDim(String code, String key, String title, String viewKey) {
            this.code = code;
            this.key = key;
            this.title = title;
            this.viewKey = viewKey;
        }

        public String getCode() {
            return this.code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getKey() {
            return this.key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getViewKey() {
            return this.viewKey;
        }

        public void setViewKey(String viewKey) {
            this.viewKey = viewKey;
        }
    }
}

