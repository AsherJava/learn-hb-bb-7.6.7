/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tag.manager.bean;

import com.jiuqi.nr.tag.manager.bean.TagObject;
import java.util.List;

public class TagManagerPara {
    private String owner;
    private String viewKey;
    private List<String> delTagKeys;
    private List<TagObject> saveTags;

    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getViewKey() {
        return this.viewKey;
    }

    public void setViewKey(String viewKey) {
        this.viewKey = viewKey;
    }

    public List<String> getDelTagKeys() {
        return this.delTagKeys;
    }

    public void setDelTagKeys(List<String> delTagKeys) {
        this.delTagKeys = delTagKeys;
    }

    public List<TagObject> getSaveTags() {
        return this.saveTags;
    }

    public void setSaveTags(List<TagObject> saveTags) {
        this.saveTags = saveTags;
    }
}

