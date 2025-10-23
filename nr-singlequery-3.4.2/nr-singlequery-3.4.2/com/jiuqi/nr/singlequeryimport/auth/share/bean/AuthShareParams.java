/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.zbquery.model.PageInfo
 */
package com.jiuqi.nr.singlequeryimport.auth.share.bean;

import com.jiuqi.nr.zbquery.model.PageInfo;
import java.util.ArrayList;
import java.util.List;

public class AuthShareParams {
    private String taskKey;
    private String formSchemeKey;
    private List<String> groupKeys = new ArrayList<String>();
    private List<String> modelIds = new ArrayList<String>();
    private List<String> userIds = new ArrayList<String>();
    private boolean isRead;
    private boolean isEdit;
    private String period;
    private PageInfo pageInfo;
    private String searchText;

    public List<String> getModelIds() {
        return this.modelIds;
    }

    public void setModelIds(List<String> modelIds) {
        this.modelIds = modelIds;
    }

    public List<String> getUserIds() {
        return this.userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public boolean getRead() {
        return this.isRead;
    }

    public void setRead(boolean read) {
        this.isRead = read;
    }

    public boolean getEdit() {
        return this.isEdit;
    }

    public void setEdit(boolean edit) {
        this.isEdit = edit;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public List<String> getGroupKeys() {
        return this.groupKeys;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public void setGroupKeys(List<String> groupKeys) {
        this.groupKeys = groupKeys;
    }

    public PageInfo getPageInfo() {
        return this.pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public String getSearchText() {
        return this.searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}

