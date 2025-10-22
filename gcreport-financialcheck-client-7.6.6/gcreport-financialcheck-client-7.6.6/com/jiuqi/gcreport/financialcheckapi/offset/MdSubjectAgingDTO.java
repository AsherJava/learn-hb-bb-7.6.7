/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckapi.offset;

import com.jiuqi.gcreport.financialcheckapi.offset.MdAgingDTO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MdSubjectAgingDTO {
    private String code;
    private String title;
    private String subjectCode;
    private Set<String> taskPeriods;
    private List<MdAgingDTO> agingList = new ArrayList<MdAgingDTO>();
    private String defaultAgingCode;

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

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public List<MdAgingDTO> getAgingList() {
        return this.agingList;
    }

    public void setAgingList(List<MdAgingDTO> agingList) {
        this.agingList = agingList;
    }

    public String getDefaultAgingCode() {
        return this.defaultAgingCode;
    }

    public void setDefaultAgingCode(String defaultAgingCode) {
        this.defaultAgingCode = defaultAgingCode;
    }

    public Set<String> getTaskPeriods() {
        return this.taskPeriods;
    }

    public boolean setAndMatchTaskPeriods(List<String> taskPeriods, String taskPeriodCode) {
        this.taskPeriods = new HashSet<String>();
        if (null != taskPeriods) {
            this.taskPeriods.addAll(taskPeriods);
        }
        boolean match = this.taskPeriods.isEmpty() || this.taskPeriods.contains(taskPeriodCode);
        return match;
    }
}

