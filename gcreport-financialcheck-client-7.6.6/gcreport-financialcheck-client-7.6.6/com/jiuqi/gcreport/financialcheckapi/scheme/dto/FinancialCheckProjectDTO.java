/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckapi.scheme.dto;

import java.util.List;
import java.util.Map;

public class FinancialCheckProjectDTO {
    private String id;
    private String checkProject;
    private String checkProjectTitle;
    private Integer checkProjectDirection;
    private List<String> subjects;
    private String oppSubject;
    private Map<String, String> oppSubjectItems;
    private List<Map<String, String>> subjectItems;
    private Integer businessRole;

    public String getCheckProject() {
        return this.checkProject;
    }

    public void setCheckProject(String checkProject) {
        this.checkProject = checkProject;
    }

    public Integer getCheckProjectDirection() {
        return this.checkProjectDirection;
    }

    public void setCheckProjectDirection(Integer checkProjectDirection) {
        this.checkProjectDirection = checkProjectDirection;
    }

    public Integer getBusinessRole() {
        return this.businessRole;
    }

    public void setBusinessRole(Integer businessRole) {
        this.businessRole = businessRole;
    }

    public List<String> getSubjects() {
        return this.subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public String getCheckProjectTitle() {
        return this.checkProjectTitle;
    }

    public void setCheckProjectTitle(String checkProjectTitle) {
        this.checkProjectTitle = checkProjectTitle;
    }

    public List<Map<String, String>> getSubjectItems() {
        return this.subjectItems;
    }

    public void setSubjectItems(List<Map<String, String>> subjectItems) {
        this.subjectItems = subjectItems;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOppSubject() {
        return this.oppSubject;
    }

    public void setOppSubject(String oppSubject) {
        this.oppSubject = oppSubject;
    }

    public Map<String, String> getOppSubjectItems() {
        return this.oppSubjectItems;
    }

    public void setOppSubjectItems(Map<String, String> oppSubjectItems) {
        this.oppSubjectItems = oppSubjectItems;
    }
}

