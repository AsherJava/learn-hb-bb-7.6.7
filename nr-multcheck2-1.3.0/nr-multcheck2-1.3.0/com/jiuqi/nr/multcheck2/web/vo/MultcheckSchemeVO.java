/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.multcheck2.web.vo;

import com.jiuqi.nr.multcheck2.common.OrgType;
import com.jiuqi.nr.multcheck2.common.SchemeType;
import java.util.ArrayList;
import java.util.List;

public class MultcheckSchemeVO {
    private String key;
    private String code;
    private String title;
    private String task;
    private String formScheme;
    private String toPeriod;
    private OrgType orgType;
    private String orgFml;
    private SchemeType type;
    private String order;
    private boolean initCheckItem;
    private String desc;
    private int orgCount;
    private List<String> orgs = new ArrayList<String>();
    private String org;
    private String rule;
    private String ruleTitle;

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

    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getFormScheme() {
        return this.formScheme;
    }

    public void setFormScheme(String formScheme) {
        this.formScheme = formScheme;
    }

    public String getToPeriod() {
        return this.toPeriod;
    }

    public void setToPeriod(String toPeriod) {
        this.toPeriod = toPeriod;
    }

    public OrgType getOrgType() {
        return this.orgType;
    }

    public void setOrgType(OrgType orgType) {
        this.orgType = orgType;
    }

    public String getOrgFml() {
        return this.orgFml;
    }

    public void setOrgFml(String orgFml) {
        this.orgFml = orgFml;
    }

    public SchemeType getType() {
        return this.type;
    }

    public void setType(SchemeType type) {
        this.type = type;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public boolean isInitCheckItem() {
        return this.initCheckItem;
    }

    public void setInitCheckItem(boolean initCheckItem) {
        this.initCheckItem = initCheckItem;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<String> getOrgs() {
        return this.orgs;
    }

    public void setOrgs(List<String> orgs) {
        this.orgs = orgs;
    }

    public int getOrgCount() {
        return this.orgCount;
    }

    public void setOrgCount(int orgCount) {
        this.orgCount = orgCount;
    }

    public String getOrg() {
        return this.org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getRule() {
        return this.rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getRuleTitle() {
        return this.ruleTitle;
    }

    public void setRuleTitle(String ruleTitle) {
        this.ruleTitle = ruleTitle;
    }
}

