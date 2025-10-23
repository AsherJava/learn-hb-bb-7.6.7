/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.access.extend.param;

import com.jiuqi.nr.data.access.extend.param.FormData;
import java.util.ArrayList;
import java.util.List;

public class FormGroupData {
    private String key;
    private String title = "\u65e0\u5206\u7ec4";
    private String code = "unGroup";
    private List<FormGroupData> groups = new ArrayList<FormGroupData>();
    private List<FormData> reports = new ArrayList<FormData>();

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<FormGroupData> getGroups() {
        return this.groups;
    }

    public void setGroups(List<FormGroupData> groups) {
        this.groups = groups;
    }

    public List<FormData> getReports() {
        return this.reports;
    }

    public void setReports(List<FormData> reports) {
        this.reports = reports;
    }

    public void addForm(FormData form) {
        this.reports.add(form);
    }
}

