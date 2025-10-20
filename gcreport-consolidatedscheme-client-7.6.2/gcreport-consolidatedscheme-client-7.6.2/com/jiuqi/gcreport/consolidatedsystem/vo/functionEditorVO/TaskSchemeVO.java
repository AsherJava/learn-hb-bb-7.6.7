/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.consolidatedsystem.vo.functionEditorVO;

import java.util.List;

public class TaskSchemeVO {
    private String title;
    private String key;
    private List<TaskSchemeVO> children;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<TaskSchemeVO> getChildren() {
        return this.children;
    }

    public void setChildren(List<TaskSchemeVO> children) {
        this.children = children;
    }
}

