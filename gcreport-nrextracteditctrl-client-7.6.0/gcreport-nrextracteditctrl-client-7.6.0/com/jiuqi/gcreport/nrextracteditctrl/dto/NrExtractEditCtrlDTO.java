/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.nrextracteditctrl.dto;

import com.jiuqi.gcreport.nrextracteditctrl.dto.FormSelection;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class NrExtractEditCtrlDTO {
    public static final String ALL_UNITCODE = "\u5168\u90e8";
    public static final String ALL_FORM = "\u5168\u90e8";
    private String id;
    private String taskKey;
    private String taskTitle;
    private String orgTypeLastValidTime;
    private String orgTypeCode;
    private String formSchemeKey;
    private String formSchemeTitle;
    private boolean allOrgFlag;
    private List<String> orgs;
    private String orgNames;
    private boolean allFormFlag;
    private boolean selectFormFlag;
    private boolean selectLinkFlag;
    private List<FormSelection> forms;
    private String formNames;
    private String linkNames;
    private List<Map<String, Object>> linksList;
    private int stopFlag;
    private Date createTime;

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

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
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

    public String getFormSchemeTitle() {
        return this.formSchemeTitle;
    }

    public void setFormSchemeTitle(String formSchemeTitle) {
        this.formSchemeTitle = formSchemeTitle;
    }

    public List<String> getOrgs() {
        return this.orgs;
    }

    public void setOrgs(List<String> orgs) {
        this.orgs = orgs;
    }

    public List<FormSelection> getForms() {
        return this.forms;
    }

    public void setForms(List<FormSelection> forms) {
        this.forms = forms;
    }

    public String getOrgNames() {
        return this.orgNames;
    }

    public void setOrgNames(String orgNames) {
        this.orgNames = orgNames;
    }

    public String getFormNames() {
        return this.formNames;
    }

    public void setFormNames(String formNames) {
        this.formNames = formNames;
    }

    public int getStopFlag() {
        return this.stopFlag;
    }

    public void setStopFlag(int stopFlag) {
        this.stopFlag = stopFlag;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getLinkNames() {
        return this.linkNames;
    }

    public void setLinkNames(String linkNames) {
        this.linkNames = linkNames;
    }

    public List<Map<String, Object>> getLinksList() {
        return this.linksList;
    }

    public void setLinksList(List<Map<String, Object>> linksList) {
        this.linksList = linksList;
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
}

