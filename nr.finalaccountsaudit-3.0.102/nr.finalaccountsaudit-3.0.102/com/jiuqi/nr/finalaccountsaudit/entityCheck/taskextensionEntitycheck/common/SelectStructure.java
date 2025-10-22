/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common;

import java.util.Objects;

public class SelectStructure {
    private String key;
    private String title;

    public SelectStructure() {
    }

    public SelectStructure(String key, String title) {
        this.key = key;
        this.title = title;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (null == o || this.getClass() != o.getClass()) {
            return false;
        }
        SelectStructure that = (SelectStructure)o;
        return Objects.equals(this.key, that.key);
    }

    public int hashCode() {
        return Objects.hash(this.key);
    }
}

