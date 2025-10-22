/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacrud.impl;

import java.util.Set;

public class EntityQueryInfo {
    private String entityId;
    private String dataLinkKey;
    private Set<String> captionFields;
    private Set<String> dropDownFields;
    private boolean readAuth = true;
    private String parentKey;
    private boolean allChildren = true;
    private String search;
    private boolean searchLeaf = false;
    private boolean matchAll = false;
    private boolean fullPath = false;

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getDataLinkKey() {
        return this.dataLinkKey;
    }

    public void setDataLinkKey(String dataLinkKey) {
        this.dataLinkKey = dataLinkKey;
    }

    public Set<String> getCaptionFields() {
        return this.captionFields;
    }

    public void setCaptionFields(Set<String> captionFields) {
        this.captionFields = captionFields;
    }

    public Set<String> getDropDownFields() {
        return this.dropDownFields;
    }

    public void setDropDownFields(Set<String> dropDownFields) {
        this.dropDownFields = dropDownFields;
    }

    public boolean isReadAuth() {
        return this.readAuth;
    }

    public void setReadAuth(boolean readAuth) {
        this.readAuth = readAuth;
    }

    public String getParentKey() {
        return this.parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public boolean isAllChildren() {
        return this.allChildren;
    }

    public void setAllChildren(boolean allChildren) {
        this.allChildren = allChildren;
    }

    public String getSearch() {
        return this.search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public boolean isSearchLeaf() {
        return this.searchLeaf;
    }

    public void setSearchLeaf(boolean searchLeaf) {
        this.searchLeaf = searchLeaf;
    }

    public boolean isMatchAll() {
        return this.matchAll;
    }

    public void setMatchAll(boolean matchAll) {
        this.matchAll = matchAll;
    }

    public boolean isFullPath() {
        return this.fullPath;
    }

    public void setFullPath(boolean fullPath) {
        this.fullPath = fullPath;
    }
}

