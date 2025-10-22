/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 */
package com.jiuqi.nr.datacheck.dataanalyze;

import com.jiuqi.nr.common.itree.INode;

public class AnalyzeModel
implements INode {
    private String key;
    private String code;
    private String title;
    private String fml;
    private String type;

    public AnalyzeModel() {
    }

    public AnalyzeModel(String key, String code, String title, String fml, String type) {
        this.key = key;
        this.code = code;
        this.title = title;
        this.fml = fml;
        this.type = type;
    }

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

    public String getFml() {
        return this.fml;
    }

    public void setFml(String fml) {
        this.fml = fml;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

