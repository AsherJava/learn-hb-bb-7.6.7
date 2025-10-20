/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckapi.checkconfig.vo;

import java.util.List;
import java.util.Map;

public class FinancialCheckConfigVO {
    private String id;
    private String checkWay;
    private String dataSource;
    private String orgType;
    private List<String> orgRange;
    private List<Map<String, Object>> orgRangeMap;
    private List<String> subjectRange;
    private List<Map<String, Object>> subjectRangeMap;
    private Map<String, Object> options;

    public String getCheckWay() {
        return this.checkWay;
    }

    public void setCheckWay(String checkWay) {
        this.checkWay = checkWay;
    }

    public String getDataSource() {
        return this.dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public List<String> getOrgRange() {
        return this.orgRange;
    }

    public void setOrgRange(List<String> orgRange) {
        this.orgRange = orgRange;
    }

    public List<String> getSubjectRange() {
        return this.subjectRange;
    }

    public void setSubjectRange(List<String> subjectRange) {
        this.subjectRange = subjectRange;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Map<String, Object>> getOrgRangeMap() {
        return this.orgRangeMap;
    }

    public void setOrgRangeMap(List<Map<String, Object>> orgRangeMap) {
        this.orgRangeMap = orgRangeMap;
    }

    public List<Map<String, Object>> getSubjectRangeMap() {
        return this.subjectRangeMap;
    }

    public void setSubjectRangeMap(List<Map<String, Object>> subjectRangeMap) {
        this.subjectRangeMap = subjectRangeMap;
    }

    public Map<String, Object> getOptions() {
        return this.options;
    }

    public void setOptions(Map<String, Object> options) {
        this.options = options;
    }
}

