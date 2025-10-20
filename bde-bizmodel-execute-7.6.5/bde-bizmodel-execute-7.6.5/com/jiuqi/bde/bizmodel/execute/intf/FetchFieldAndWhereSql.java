/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.execute.intf;

import com.jiuqi.bde.bizmodel.execute.intf.FetchDataAssQueryCondi;
import java.util.List;

public class FetchFieldAndWhereSql {
    private FetchDataAssQueryCondi fetchDataAssQueryCondi;
    private List<String> selectFieldSql;
    private List<String> groupFieldSql;
    private List<String> whereFieldSql;
    private List<String> fetchTypes;
    private boolean cleanZeroRecords;
    private String orgCode;
    private String orgType;
    private String orgVer;

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getOrgVer() {
        return this.orgVer;
    }

    public void setOrgVer(String orgVer) {
        this.orgVer = orgVer;
    }

    public FetchDataAssQueryCondi getFetchDataAssQueryCondi() {
        return this.fetchDataAssQueryCondi;
    }

    public void setFetchDataAssQueryCondi(FetchDataAssQueryCondi fetchDataAssQueryCondi) {
        this.fetchDataAssQueryCondi = fetchDataAssQueryCondi;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public List<String> getFetchTypes() {
        return this.fetchTypes;
    }

    public void setFetchTypes(List<String> fetchTypes) {
        this.fetchTypes = fetchTypes;
    }

    public FetchFieldAndWhereSql(List<String> sumFieldSql, List<String> groupFieldSql, List<String> whereFieldSql) {
        this.selectFieldSql = sumFieldSql;
        this.groupFieldSql = groupFieldSql;
        this.whereFieldSql = whereFieldSql;
    }

    public FetchFieldAndWhereSql(List<String> selectFieldSql, List<String> groupFieldSql, List<String> whereFieldSql, String orgCode, String orgVer, String orgType, FetchDataAssQueryCondi fetchDataAssQueryCondi) {
        this.selectFieldSql = selectFieldSql;
        this.groupFieldSql = groupFieldSql;
        this.whereFieldSql = whereFieldSql;
        this.orgCode = orgCode;
        this.orgVer = orgVer;
        this.orgType = orgType;
        this.fetchDataAssQueryCondi = fetchDataAssQueryCondi;
    }

    public List<String> getSelectFieldSql() {
        return this.selectFieldSql;
    }

    public void setSelectFieldSql(List<String> selectFieldSql) {
        this.selectFieldSql = selectFieldSql;
    }

    public List<String> getGroupFieldSql() {
        return this.groupFieldSql;
    }

    public void setGroupFieldSql(List<String> groupFieldSql) {
        this.groupFieldSql = groupFieldSql;
    }

    public List<String> getWhereFieldSql() {
        return this.whereFieldSql;
    }

    public void setWhereFieldSql(List<String> whereFieldSql) {
        this.whereFieldSql = whereFieldSql;
    }

    public boolean isCleanZeroRecords() {
        return this.cleanZeroRecords;
    }

    public void setCleanZeroRecords(boolean cleanZeroRecords) {
        this.cleanZeroRecords = cleanZeroRecords;
    }
}

