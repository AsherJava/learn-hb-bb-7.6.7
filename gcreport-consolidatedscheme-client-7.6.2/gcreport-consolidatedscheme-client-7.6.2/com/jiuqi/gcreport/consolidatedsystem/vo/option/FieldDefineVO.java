/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.consolidatedsystem.vo.option;

import java.util.List;

public class FieldDefineVO {
    private String code;
    private String title;
    private int type;
    private List<FieldDefineVO> children;

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

    public List<FieldDefineVO> getChildren() {
        return this.children;
    }

    public void setChildren(List<FieldDefineVO> children) {
        this.children = children;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

