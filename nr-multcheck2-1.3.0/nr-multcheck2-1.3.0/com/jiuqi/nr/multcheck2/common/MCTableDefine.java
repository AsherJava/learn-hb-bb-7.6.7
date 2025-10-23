/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.multcheck2.common;

import com.jiuqi.nr.multcheck2.common.MCColumnDefine;
import java.util.List;

public class MCTableDefine {
    private String name;
    private String title;
    private String key;
    private List<MCColumnDefine> cols;

    public MCTableDefine() {
    }

    public MCTableDefine(String name, String title, List<MCColumnDefine> cols, String key) {
        this.name = name;
        this.title = title;
        this.cols = cols;
        this.key = key;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<MCColumnDefine> getCols() {
        return this.cols;
    }

    public void setCols(List<MCColumnDefine> cols) {
        this.cols = cols;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

