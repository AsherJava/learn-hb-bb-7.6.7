/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.nrextracteditctrl.dto;

import java.util.List;
import java.util.Map;

public class NrExtractEditCtrlSaveDTO {
    private String id;
    private String taskKey;
    private String orgTypeLastValidTime;
    private String orgTypeCode;
    private String formSchemeKey;
    private boolean allOrgFlag;
    private List<String> orgs;
    private boolean allFormFlag;
    private List<String> forms;
    private boolean selectFormFlag;
    private boolean selectLinkFlag;
    private Map<String, List<String>> linksMap;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getOrgTypeLastValidTime() {
        return this.orgTypeLastValidTime;
    }

    public void setOrgTypeLastValidTime(String orgTypeLastValidTime) {
        this.orgTypeLastValidTime = orgTypeLastValidTime;
    }

    public String getOrgTypeCode() {
        return this.orgTypeCode;
    }

    public void setOrgTypeCode(String orgTypeCode) {
        this.orgTypeCode = orgTypeCode;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public List<String> getOrgs() {
        return this.orgs;
    }

    public void setOrgs(List<String> orgs) {
        this.orgs = orgs;
    }

    public List<String> getForms() {
        return this.forms;
    }

    public void setForms(List<String> forms) {
        this.forms = forms;
    }

    public boolean isAllOrgFlag() {
        return this.allOrgFlag;
    }

    public void setAllOrgFlag(boolean allOrgFlag) {
        this.allOrgFlag = allOrgFlag;
    }

    public boolean isAllFormFlag() {
        return this.allFormFlag;
    }

    public void setAllFormFlag(boolean allFormFlag) {
        this.allFormFlag = allFormFlag;
    }

    public boolean isSelectFormFlag() {
        return this.selectFormFlag;
    }

    public void setSelectFormFlag(boolean selectFormFlag) {
        this.selectFormFlag = selectFormFlag;
    }

    public boolean isSelectLinkFlag() {
        return this.selectLinkFlag;
    }

    public void setSelectLinkFlag(boolean selectLinkFlag) {
        this.selectLinkFlag = selectLinkFlag;
    }

    public Map<String, List<String>> getLinksMap() {
        return this.linksMap;
    }

    public void setLinksMap(Map<String, List<String>> linksMap) {
        this.linksMap = linksMap;
    }
}

