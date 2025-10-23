/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.api.service.entity.dto;

import com.jiuqi.nr.task.api.tree.TreeData;
import java.util.List;

public class EntityDataDTO
implements TreeData {
    private String key;
    private String code;
    private String title;
    private String parent;
    private List<String> path;

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getParent() {
        return this.parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public List<String> getPath() {
        return this.path;
    }

    public void setPath(List<String> path) {
        this.path = path;
    }
}

