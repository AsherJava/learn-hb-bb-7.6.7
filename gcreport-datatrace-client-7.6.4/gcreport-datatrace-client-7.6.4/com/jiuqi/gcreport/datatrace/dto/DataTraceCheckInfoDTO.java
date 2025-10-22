/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 */
package com.jiuqi.gcreport.datatrace.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DataTraceCheckInfoDTO {
    private String ruleId;
    private String ruleTitle;
    private String subjectCode;
    private String subjectTitle;
    private String formula;
    private String offsetDebitInfo;
    private String offsetCreditInfo;
    private String diffInfo;
    private String checkOffsetDebitInfo;
    private String checkOffsetCreditInfo;
    private String checkInfo;
    @JsonIgnore
    private List<OriginItem> originItems;
    @JsonIgnore
    private List<CheckItem> checkItems;

    public DataTraceCheckInfoDTO(String ruleId, String subjectCode, String formula) {
        this.ruleId = ruleId;
        this.subjectCode = subjectCode;
        this.formula = formula;
        this.originItems = new ArrayList<OriginItem>();
        this.checkItems = new ArrayList<CheckItem>();
    }

    public String getDiffInfo() {
        return this.diffInfo;
    }

    public void setDiffInfo(String diffInfo) {
        this.diffInfo = diffInfo;
    }

    public String getRuleId() {
        return this.ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public List<OriginItem> getOriginItems() {
        return this.originItems;
    }

    public List<CheckItem> getCheckItems() {
        return this.checkItems;
    }

    public String getOffsetDebitInfo() {
        return this.offsetDebitInfo;
    }

    public void setOffsetDebitInfo(String offsetDebitInfo) {
        this.offsetDebitInfo = offsetDebitInfo;
    }

    public String getOffsetCreditInfo() {
        return this.offsetCreditInfo;
    }

    public void setOffsetCreditInfo(String offsetCreditInfo) {
        this.offsetCreditInfo = offsetCreditInfo;
    }

    public String getCheckOffsetDebitInfo() {
        return this.checkOffsetDebitInfo;
    }

    public void setCheckOffsetDebitInfo(String checkOffsetDebitInfo) {
        this.checkOffsetDebitInfo = checkOffsetDebitInfo;
    }

    public String getCheckOffsetCreditInfo() {
        return this.checkOffsetCreditInfo;
    }

    public void setCheckOffsetCreditInfo(String checkOffsetCreditInfo) {
        this.checkOffsetCreditInfo = checkOffsetCreditInfo;
    }

    public String getCheckInfo() {
        return this.checkInfo;
    }

    public void setCheckInfo(String checkInfo) {
        this.checkInfo = checkInfo;
    }

    public String getRuleTitle() {
        return this.ruleTitle;
    }

    public void setRuleTitle(String ruleTitle) {
        this.ruleTitle = ruleTitle;
    }

    public String getSubjectTitle() {
        return this.subjectTitle;
    }

    public void setSubjectTitle(String subjectTitle) {
        this.subjectTitle = subjectTitle;
    }

    public static class CheckItem
    implements Comparable<CheckItem> {
        private BigDecimal checkOffsetDebit;
        private BigDecimal checkOffsetCredit;

        public CheckItem(BigDecimal checkOffsetDebit, BigDecimal checkOffsetCredit) {
            this.checkOffsetDebit = checkOffsetDebit;
            this.checkOffsetCredit = checkOffsetCredit;
        }

        public BigDecimal getCheckOffsetDebit() {
            return this.checkOffsetDebit;
        }

        public void setCheckOffsetDebit(BigDecimal checkOffsetDebit) {
            this.checkOffsetDebit = checkOffsetDebit;
        }

        public BigDecimal getCheckOffsetCredit() {
            return this.checkOffsetCredit;
        }

        public void setCheckOffsetCredit(BigDecimal checkOffsetCredit) {
            this.checkOffsetCredit = checkOffsetCredit;
        }

        @Override
        public int compareTo(CheckItem o) {
            int i = o.getCheckOffsetCredit().compareTo(this.checkOffsetCredit);
            if (i == 0) {
                i = o.getCheckOffsetDebit().compareTo(this.checkOffsetDebit);
            }
            return i;
        }
    }

    public static class OriginItem
    implements Comparable<OriginItem> {
        private BigDecimal offsetDebit;
        private BigDecimal offsetCredit;
        private BigDecimal diff;

        public OriginItem(BigDecimal offsetDebit, BigDecimal offsetCredit, BigDecimal diff) {
            this.offsetDebit = offsetDebit;
            this.offsetCredit = offsetCredit;
            this.diff = diff;
        }

        public BigDecimal getOffsetDebit() {
            return this.offsetDebit;
        }

        public void setOffsetDebit(BigDecimal offsetDebit) {
            this.offsetDebit = offsetDebit;
        }

        public BigDecimal getOffsetCredit() {
            return this.offsetCredit;
        }

        public void setOffsetCredit(BigDecimal offsetCredit) {
            this.offsetCredit = offsetCredit;
        }

        public BigDecimal getDiff() {
            return this.diff;
        }

        public void setDiff(BigDecimal diff) {
            this.diff = diff;
        }

        @Override
        public int compareTo(OriginItem o) {
            int i = o.getOffsetCredit().compareTo(this.offsetCredit);
            if (i == 0) {
                i = o.getOffsetDebit().compareTo(this.offsetDebit);
            }
            return i;
        }
    }
}

