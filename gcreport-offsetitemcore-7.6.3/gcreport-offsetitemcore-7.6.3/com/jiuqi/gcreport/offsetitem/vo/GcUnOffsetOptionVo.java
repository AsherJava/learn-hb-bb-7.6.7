/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.vo;

import java.util.List;

public class GcUnOffsetOptionVo {
    protected String id;
    protected String code;
    protected String title;
    protected List<String> effectRange;
    protected String content;
    protected Integer ordinal;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<String> getEffectRange() {
        return this.effectRange;
    }

    public void setEffectRange(List<String> effectRange) {
        this.effectRange = effectRange;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(Integer ordinal) {
        this.ordinal = ordinal;
    }
}

