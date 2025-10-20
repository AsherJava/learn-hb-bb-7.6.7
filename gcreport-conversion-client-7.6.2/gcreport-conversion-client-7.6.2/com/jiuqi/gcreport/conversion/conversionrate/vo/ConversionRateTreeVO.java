/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.conversion.conversionrate.vo;

import java.util.List;

public class ConversionRateTreeVO {
    private String periodId;
    private String periodTitle;
    private String title;
    private List<ConvertRate> children;

    public String getTitle() {
        if (this.title == null) {
            this.title = this.periodTitle;
        }
        return this.title;
    }

    public String getPeriodId() {
        return this.periodId;
    }

    public void setPeriodId(String periodId) {
        this.periodId = periodId;
    }

    public String getPeriodTitle() {
        return this.periodTitle;
    }

    public void setPeriodTitle(String periodTitle) {
        this.periodTitle = periodTitle;
    }

    public List<ConvertRate> getChildren() {
        return this.children;
    }

    public void setChildren(List<ConvertRate> children) {
        this.children = children;
    }

    public class ConvertRate {
        private String periodId;
        private String periodYear;
        private String periodMonth;
        private String sourceCurrencyCode;
        private String sourceCurrencyTitle;
        private String targetCurrencyCode;
        private String targetCurrencyTitle;
        private String treeNodeName;
        private String title;

        public String getPeriodId() {
            return this.periodId;
        }

        public void setPeriodId(String periodId) {
            this.periodId = periodId;
        }

        public String getPeriodYear() {
            return this.periodYear;
        }

        public void setPeriodYear(String periodYear) {
            this.periodYear = periodYear;
        }

        public String getPeriodMonth() {
            return this.periodMonth;
        }

        public void setPeriodMonth(String periodMonth) {
            this.periodMonth = periodMonth;
        }

        public String getSourceCurrencyCode() {
            return this.sourceCurrencyCode;
        }

        public void setSourceCurrencyCode(String sourceCurrencyCode) {
            this.sourceCurrencyCode = sourceCurrencyCode;
        }

        public String getSourceCurrencyTitle() {
            return this.sourceCurrencyTitle;
        }

        public void setSourceCurrencyTitle(String sourceCurrencyTitle) {
            this.sourceCurrencyTitle = sourceCurrencyTitle;
        }

        public String getTargetCurrencyCode() {
            return this.targetCurrencyCode;
        }

        public void setTargetCurrencyCode(String targetCurrencyCode) {
            this.targetCurrencyCode = targetCurrencyCode;
        }

        public String getTargetCurrencyTitle() {
            return this.targetCurrencyTitle;
        }

        public void setTargetCurrencyTitle(String targetCurrencyTitle) {
            this.targetCurrencyTitle = targetCurrencyTitle;
        }

        public String getTreeNodeName() {
            if (this.getSourceCurrencyTitle() != null && this.getTargetCurrencyTitle() != null) {
                this.treeNodeName = this.sourceCurrencyTitle + "-" + this.targetCurrencyTitle;
            }
            return this.treeNodeName;
        }

        public String getTitle() {
            if (this.title == null) {
                this.title = this.treeNodeName;
            }
            return this.title;
        }
    }
}

