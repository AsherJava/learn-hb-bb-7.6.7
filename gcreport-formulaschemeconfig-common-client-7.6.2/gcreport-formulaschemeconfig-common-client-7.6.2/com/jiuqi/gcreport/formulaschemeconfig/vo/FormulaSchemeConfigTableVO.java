/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.org.OrgDO
 */
package com.jiuqi.gcreport.formulaschemeconfig.vo;

import com.jiuqi.va.domain.org.OrgDO;
import java.io.Serializable;
import java.util.Date;

public class FormulaSchemeConfigTableVO
implements Serializable {
    private int index;
    private String id;
    private String orgId;
    private String orgTitle;
    private OrgDO orgUnit;
    private String category;
    private String collocationMethodId;
    private String collocationMethod;
    private String strategyUnit;
    private String fetchSchemeId;
    private String fetchScheme;
    private String creator;
    private Date createTime;

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgId() {
        return this.orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgTitle() {
        return this.orgTitle;
    }

    public void setOrgTitle(String orgTitle) {
        this.orgTitle = orgTitle;
    }

    public OrgDO getOrgUnit() {
        return this.orgUnit;
    }

    public void setOrgUnit(OrgDO orgUnit) {
        this.orgUnit = orgUnit;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCollocationMethodId() {
        return this.collocationMethodId;
    }

    public void setCollocationMethodId(String collocationMethodId) {
        this.collocationMethodId = collocationMethodId;
    }

    public String getCollocationMethod() {
        return this.collocationMethod;
    }

    public void setCollocationMethod(String collocationMethod) {
        this.collocationMethod = collocationMethod;
    }

    public String getStrategyUnit() {
        return this.strategyUnit;
    }

    public void setStrategyUnit(String strategyUnit) {
        this.strategyUnit = strategyUnit;
    }

    public String getFetchSchemeId() {
        return this.fetchSchemeId;
    }

    public void setFetchSchemeId(String fetchSchemeId) {
        this.fetchSchemeId = fetchSchemeId;
    }

    public String getFetchScheme() {
        return this.fetchScheme;
    }

    public void setFetchScheme(String fetchScheme) {
        this.fetchScheme = fetchScheme;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

