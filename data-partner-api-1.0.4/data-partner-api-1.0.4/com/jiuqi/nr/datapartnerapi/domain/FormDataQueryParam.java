/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datapartnerapi.domain;

import com.jiuqi.nr.datapartnerapi.domain.FieldValueFilter;
import java.util.ArrayList;
import java.util.List;

public class FormDataQueryParam {
    private String taskKey;
    private final List<String> mdCode = new ArrayList<String>();
    private final List<String> datatime = new ArrayList<String>();
    private final List<String> queryForms = new ArrayList<String>();
    private final List<String> queryFields = new ArrayList<String>();
    private final List<FieldValueFilter> filters = new ArrayList<FieldValueFilter>();
    private String entityID;

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public List<String> getMdCode() {
        return this.mdCode;
    }

    public List<String> getDatatime() {
        return this.datatime;
    }

    public List<String> getQueryForms() {
        return this.queryForms;
    }

    public List<String> getQueryFields() {
        return this.queryFields;
    }

    public List<FieldValueFilter> getFilters() {
        return this.filters;
    }

    public String getEntityID() {
        return this.entityID;
    }

    public void setEntityID(String entityID) {
        this.entityID = entityID;
    }
}

