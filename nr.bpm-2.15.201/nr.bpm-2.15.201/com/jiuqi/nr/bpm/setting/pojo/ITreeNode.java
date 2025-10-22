/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.setting.pojo;

import com.jiuqi.nr.bpm.setting.pojo.Entites;
import java.util.List;

public class ITreeNode {
    private String key;
    private String title;
    private boolean expand;
    private List<ITreeNode> children;
    private String entityKey;
    private List<Entites> entityList;
    private String period;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isExpand() {
        return this.expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public List<ITreeNode> getChildren() {
        return this.children;
    }

    public void setChildren(List<ITreeNode> children) {
        this.children = children;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEntityKey() {
        return this.entityKey;
    }

    public void setEntityKey(String entityKey) {
        this.entityKey = entityKey;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public List<Entites> getEntityList() {
        return this.entityList;
    }

    public void setEntityList(List<Entites> entityList) {
        this.entityList = entityList;
    }
}

