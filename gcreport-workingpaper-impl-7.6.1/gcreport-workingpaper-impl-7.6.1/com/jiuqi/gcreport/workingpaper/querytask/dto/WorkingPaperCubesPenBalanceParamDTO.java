/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.workingpaper.querytask.dto;

import java.util.List;
import java.util.Map;

public class WorkingPaperCubesPenBalanceParamDTO {
    private Map<String, Object> selectUnit;
    private Map<String, Object> selectStartSub;
    private Map<String, Object> selectEndSub;
    private List<String> unitCodes;
    private boolean reportFlag;
    private int acctYear;
    private int startPeriod;
    private int endPeriod;
    private String currencyCode;
    private String repCurrCode;
    private String startSubjectCode;
    private String endSubjectCode;
    private List<QueryDim> queryDimList;
    private String finCurr;
    private boolean containAdjustVchr;
    private String excludeSubj;
    private int page;
    private int pageSize;

    public Map<String, Object> getSelectUnit() {
        return this.selectUnit;
    }

    public void setSelectUnit(Map<String, Object> selectUnit) {
        this.selectUnit = selectUnit;
    }

    public Map<String, Object> getSelectStartSub() {
        return this.selectStartSub;
    }

    public void setSelectStartSub(Map<String, Object> selectStartSub) {
        this.selectStartSub = selectStartSub;
    }

    public Map<String, Object> getSelectEndSub() {
        return this.selectEndSub;
    }

    public void setSelectEndSub(Map<String, Object> selectEndSub) {
        this.selectEndSub = selectEndSub;
    }

    public List<String> getUnitCodes() {
        return this.unitCodes;
    }

    public void setUnitCodes(List<String> unitCodes) {
        this.unitCodes = unitCodes;
    }

    public boolean isReportFlag() {
        return this.reportFlag;
    }

    public void setReportFlag(boolean reportFlag) {
        this.reportFlag = reportFlag;
    }

    public int getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(int acctYear) {
        this.acctYear = acctYear;
    }

    public int getStartPeriod() {
        return this.startPeriod;
    }

    public void setStartPeriod(int startPeriod) {
        this.startPeriod = startPeriod;
    }

    public int getEndPeriod() {
        return this.endPeriod;
    }

    public void setEndPeriod(int endPeriod) {
        this.endPeriod = endPeriod;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getRepCurrCode() {
        return this.repCurrCode;
    }

    public void setRepCurrCode(String repCurrCode) {
        this.repCurrCode = repCurrCode;
    }

    public String getStartSubjectCode() {
        return this.startSubjectCode;
    }

    public void setStartSubjectCode(String startSubjectCode) {
        this.startSubjectCode = startSubjectCode;
    }

    public String getEndSubjectCode() {
        return this.endSubjectCode;
    }

    public void setEndSubjectCode(String endSubjectCode) {
        this.endSubjectCode = endSubjectCode;
    }

    public List<QueryDim> getQueryDimList() {
        return this.queryDimList;
    }

    public void setQueryDimList(List<QueryDim> queryDimList) {
        this.queryDimList = queryDimList;
    }

    public String getFinCurr() {
        return this.finCurr;
    }

    public void setFinCurr(String finCurr) {
        this.finCurr = finCurr;
    }

    public boolean isContainAdjustVchr() {
        return this.containAdjustVchr;
    }

    public void setContainAdjustVchr(boolean containAdjustVchr) {
        this.containAdjustVchr = containAdjustVchr;
    }

    public String getExcludeSubj() {
        return this.excludeSubj;
    }

    public void setExcludeSubj(String excludeSubj) {
        this.excludeSubj = excludeSubj;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public static class QueryDim {
        private String dimCode;
        private String dimName;
        private String startCode;
        private String endCode;
        private String referField;

        public QueryDim() {
        }

        public QueryDim(String dimCode, String dimName, String startCode, String endCode, String referField) {
            this.dimCode = dimCode;
            this.dimName = dimName;
            this.startCode = startCode;
            this.endCode = endCode;
            this.referField = referField;
        }

        public String getDimCode() {
            return this.dimCode;
        }

        public void setDimCode(String dimCode) {
            this.dimCode = dimCode;
        }

        public String getDimName() {
            return this.dimName;
        }

        public void setDimName(String dimName) {
            this.dimName = dimName;
        }

        public String getStartCode() {
            return this.startCode;
        }

        public void setStartCode(String startCode) {
            this.startCode = startCode;
        }

        public String getEndCode() {
            return this.endCode;
        }

        public void setEndCode(String endCode) {
            this.endCode = endCode;
        }

        public String getReferField() {
            return this.referField;
        }

        public void setReferField(String referField) {
            this.referField = referField;
        }
    }

    public static class SelectSub {
        private String key;
        private String code;
        private String title;
        private String parentid;
        private String ordinal;
        private boolean leaf;
        private String parents;

        public SelectSub() {
        }

        public SelectSub(String key, String code, String title, String parentid, String ordinal, boolean leaf, String parents) {
            this.key = key;
            this.code = code;
            this.title = title;
            this.parentid = parentid;
            this.ordinal = ordinal;
            this.leaf = leaf;
            this.parents = parents;
        }

        public String getKey() {
            return this.key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getCode() {
            return this.code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getParentid() {
            return this.parentid;
        }

        public void setParentid(String parentid) {
            this.parentid = parentid;
        }

        public String getOrdinal() {
            return this.ordinal;
        }

        public void setOrdinal(String ordinal) {
            this.ordinal = ordinal;
        }

        public boolean isLeaf() {
            return this.leaf;
        }

        public void setLeaf(boolean leaf) {
            this.leaf = leaf;
        }

        public String getParents() {
            return this.parents;
        }

        public void setParents(String parents) {
            this.parents = parents;
        }
    }

    public static class SelectUnit {
        private String key;
        private String code;
        private String title;
        private String parentcode;
        private String ordinal;
        private boolean leaf;
        private String parents;

        public SelectUnit() {
        }

        public SelectUnit(String key, String code, String title, String parentcode, String ordinal, boolean leaf, String parents) {
            this.key = key;
            this.code = code;
            this.title = title;
            this.parentcode = parentcode;
            this.ordinal = ordinal;
            this.leaf = leaf;
            this.parents = parents;
        }

        public String getKey() {
            return this.key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getCode() {
            return this.code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getParentcode() {
            return this.parentcode;
        }

        public void setParentcode(String parentcode) {
            this.parentcode = parentcode;
        }

        public String getOrdinal() {
            return this.ordinal;
        }

        public void setOrdinal(String ordinal) {
            this.ordinal = ordinal;
        }

        public boolean isLeaf() {
            return this.leaf;
        }

        public void setLeaf(boolean leaf) {
            this.leaf = leaf;
        }

        public String getParents() {
            return this.parents;
        }

        public void setParents(String parents) {
            this.parents = parents;
        }
    }
}

