/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common;

public class MatchTypeStructure {
    private int key;
    private String title;

    public MatchTypeStructure() {
    }

    public MatchTypeStructure(int key, String title) {
        this.key = key;
        this.title = title;
    }

    public int getKey() {
        return this.key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

