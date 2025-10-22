/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckapi.scheme.dto;

import java.util.List;
import java.util.Map;

public class FinancialCheckUnilateralSubSettingDTO {
    private List<String> subjects;
    private List<Map<String, String>> subjectItems;
    private String oppSubject;
    private Map<String, String> oppSubjectItems;

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

