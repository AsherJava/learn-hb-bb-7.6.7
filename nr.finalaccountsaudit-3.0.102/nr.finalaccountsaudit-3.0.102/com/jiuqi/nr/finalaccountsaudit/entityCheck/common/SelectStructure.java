/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.common;

public class SelectStructure {
    private String key;
    private String title;
    private String toPeriod;

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

    public String getToPeriod() {
        return this.toPeriod;
    }

    public void setToPeriod(String toPeriod) {
        this.toPeriod = toPeriod;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.key == null ? 0 : this.key.hashCode());
        result = 31 * result + (this.title == null ? 0 : this.title.hashCode());
        result = 31 * result + (this.toPeriod == null ? 0 : this.toPeriod.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        SelectStructure other = (SelectStructure)obj;
        if (this.key == null ? other.key != null : !this.key.equals(other.key)) {
            return false;
        }
        if (this.title == null ? other.title != null : !this.title.equals(other.title)) {
            return false;
        }
        return !(this.toPeriod == null ? other.toPeriod != null : !this.toPeriod.equals(other.toPeriod));
    }
}

