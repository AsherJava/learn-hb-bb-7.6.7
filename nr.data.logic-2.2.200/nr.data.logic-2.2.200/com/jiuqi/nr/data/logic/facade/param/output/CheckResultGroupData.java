/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.facade.param.output;

import java.util.ArrayList;
import java.util.List;

public class CheckResultGroupData {
    private String key;
    private String code;
    private String title;
    private int count;
    private List<CheckResultGroupData> children = new ArrayList<CheckResultGroupData>();

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

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<CheckResultGroupData> getChildren() {
        return this.children;
    }

    public void setChildren(List<CheckResultGroupData> children) {
        this.children = children;
    }
}

