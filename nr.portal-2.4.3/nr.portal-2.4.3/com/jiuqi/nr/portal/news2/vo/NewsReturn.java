/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.portal.news2.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.portal.news2.impl.FileImpl;
import com.jiuqi.nr.portal.news2.impl.NewsAbstractInfo;
import com.jiuqi.nr.portal.news2.impl.NewsImpl;
import com.jiuqi.nr.portal.news2.vo.UserInfo;
import java.util.List;

public class NewsReturn {
    @JsonProperty(value="NewsDefine")
    private NewsImpl newsImpl;
    private NewsAbstractInfo[] abstractInfos;
    private Boolean modelEditable;
    private Boolean state;
    private String message;
    private List<UserInfo> users;
    private List<FileImpl> fileImpls;

    public NewsImpl getNewsImpl() {
        return this.newsImpl;
    }

    public void setNewsImpl(NewsImpl newsImpl) {
        this.newsImpl = newsImpl;
    }

    public NewsAbstractInfo[] getAbstractInfos() {
        return this.abstractInfos;
    }

    public void setAbstractInfos(NewsAbstractInfo[] abstractInfos) {
        this.abstractInfos = abstractInfos;
    }

    public Boolean getState() {
        return this.state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getModelEditable() {
        return this.modelEditable;
    }

    public void setModelEditable(Boolean modelEditable) {
        this.modelEditable = modelEditable;
    }

    public List<UserInfo> getUsers() {
        return this.users;
    }

    public void setUsers(List<UserInfo> users) {
        this.users = users;
    }

    public List<FileImpl> getFileImpls() {
        return this.fileImpls;
    }

    public void setFileImpls(List<FileImpl> fileImpls) {
        this.fileImpls = fileImpls;
    }
}

