/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.pinyin.entity;

import java.util.List;
import java.util.UUID;

public class WordEntity {
    private UUID id;
    private String pingyin;
    private String hanzi;
    private List hanziList;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPingyin() {
        return this.pingyin;
    }

    public void setPingyin(String pingyin) {
        this.pingyin = pingyin;
    }

    public String getHanzi() {
        return this.hanzi;
    }

    public void setHanzi(String hanzi) {
        this.hanzi = hanzi;
    }

    public List getHanziList() {
        return this.hanziList;
    }

    public void setHanziList(List hanziList) {
        this.hanziList = hanziList;
    }
}

