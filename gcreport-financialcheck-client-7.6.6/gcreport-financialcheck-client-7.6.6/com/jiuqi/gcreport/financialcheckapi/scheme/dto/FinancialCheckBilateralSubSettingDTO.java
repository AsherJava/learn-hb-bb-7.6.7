/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckapi.scheme.dto;

import java.util.List;
import java.util.Map;

public class FinancialCheckBilateralSubSettingDTO {
    private String group;
    private Map<String, String> groupItem;
    private List<String> subjects;
    private List<Map<String, String>> subjectItems;
    private List<String> debtSubjects;
    private List<Map<String, String>> debtSubjectItems;

    public String getGroup() {
        return this.group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<String> getSubjects() {
        return this.subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public List<Map<String, String>> getSubjectItems() {
        return this.subjectItems;
    }

    public void setSubjectItems(List<Map<String, String>> subjectItems) {
        this.subjectItems = subjectItems;
    }

    public Map<String, String> getGroupItem() {
        return this.groupItem;
    }

    public void setGroupItem(Map<String, String> groupItem) {
        this.groupItem = groupItem;
    }

    public List<String> getDebtSubjects() {
        return this.debtSubjects;
    }

    public void setDebtSubjects(List<String> debtSubjects) {
        this.debtSubjects = debtSubjects;
    }

    public List<Map<String, String>> getDebtSubjectItems() {
        return this.debtSubjectItems;
    }

    public void setDebtSubjectItems(List<Map<String, String>> debtSubjectItems) {
        this.debtSubjectItems = debtSubjectItems;
    }
}

