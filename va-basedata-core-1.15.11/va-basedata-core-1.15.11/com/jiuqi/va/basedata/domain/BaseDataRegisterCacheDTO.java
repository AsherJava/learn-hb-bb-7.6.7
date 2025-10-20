/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.basedata.domain;

import java.util.Set;

public class BaseDataRegisterCacheDTO {
    private String currNodeId;
    private String currentNodeAddr;
    private String definesIncludeStr;
    private Set<String> definesInclude;
    private Set<String> definesExclude;

    public String getCurrNodeId() {
        return this.currNodeId;
    }

    public void setCurrNodeId(String currNodeId) {
        this.currNodeId = currNodeId;
    }

    public String getCurrentNodeAddr() {
        return this.currentNodeAddr;
    }

    public void setCurrentNodeAddr(String currentNodeAddr) {
        this.currentNodeAddr = currentNodeAddr;
    }

    public String getDefinesIncludeStr() {
        return this.definesIncludeStr;
    }

    public void setDefinesIncludeStr(String definesIncludeStr) {
        this.definesIncludeStr = definesIncludeStr;
    }

    public Set<String> getDefinesInclude() {
        return this.definesInclude;
    }

    public void setDefinesInclude(Set<String> definesInclude) {
        this.definesInclude = definesInclude;
    }

    public Set<String> getDefinesExclude() {
        return this.definesExclude;
    }

    public void setDefinesExclude(Set<String> definesExclude) {
        this.definesExclude = definesExclude;
    }
}

