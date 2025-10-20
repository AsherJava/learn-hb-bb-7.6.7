/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.analysisreport.vo;

import java.io.Serializable;
import java.util.List;

public class ReportBaseVO
implements Serializable,
Cloneable {
    private String key;
    private PeriodDim period;
    private List<UnitDim> chooseUnits;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public PeriodDim getPeriod() {
        return this.period;
    }

    public String getPeriodStr() {
        if (this.period == null) {
            return null;
        }
        return this.period.getCalendarCode();
    }

    public void setPeriod(PeriodDim period) {
        this.period = period;
    }

    public List<UnitDim> getChooseUnits() {
        return this.chooseUnits;
    }

    public void setChooseUnits(List<UnitDim> chooseUnits) {
        this.chooseUnits = chooseUnits;
    }

    public static class PeriodDim
    implements Serializable,
    Cloneable {
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

    public static class UnitDim
    implements Serializable,
    Cloneable {
        private String code;
        private String title;
        private String viewKey;
        private List<String> codes;
        private boolean chooseAll;

        public UnitDim() {
        }

        public UnitDim(String code, String key, String title, String viewKey) {
            this.code = code;
            this.title = title;
            this.viewKey = viewKey;
        }

        public UnitDim(String code, String title, String viewKey, List<String> codes, boolean chooseAll) {
            this.code = code;
            this.title = title;
            this.viewKey = viewKey;
            this.codes = codes;
            this.chooseAll = chooseAll;
        }

        public String getCode() {
            return this.code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getKey() {
            return this.code;
        }

        public void setKey(String key) {
            this.code = key;
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

        public List<String> getCodes() {
            return this.codes;
        }

        public void setCodes(List<String> codes) {
            this.codes = codes;
        }

        public boolean getChooseAll() {
            return this.chooseAll;
        }

        public void setChooseAll(boolean chooseAll) {
            this.chooseAll = chooseAll;
        }

        protected UnitDim clone() {
            UnitDim unitDim = null;
            try {
                unitDim = (UnitDim)super.clone();
            }
            catch (CloneNotSupportedException e) {
                unitDim = new UnitDim();
            }
            return unitDim;
        }
    }
}

