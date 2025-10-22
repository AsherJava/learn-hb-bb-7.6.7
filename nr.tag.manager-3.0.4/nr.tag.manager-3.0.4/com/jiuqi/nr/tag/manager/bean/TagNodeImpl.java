/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tag.manager.bean;

public class TagNodeImpl {
    private String tgKey;
    private String entKey;
    private String viewkey;

    public String getTgKey() {
        return this.tgKey;
    }

    public void setTgKey(String tgKey) {
        this.tgKey = tgKey;
    }

    public String getEntKey() {
        return this.entKey;
    }

    public void setEntKey(String entKey) {
        this.entKey = entKey;
    }

    public String getViewkey() {
        return this.viewkey;
    }

    public void setViewkey(String viewkey) {
        this.viewkey = viewkey;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.entKey == null ? 0 : this.entKey.hashCode());
        result = 31 * result + (this.tgKey == null ? 0 : this.tgKey.hashCode());
        result = 31 * result + (this.viewkey == null ? 0 : this.viewkey.hashCode());
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
        TagNodeImpl other = (TagNodeImpl)obj;
        if (this.entKey == null ? other.entKey != null : !this.entKey.equals(other.entKey)) {
            return false;
        }
        if (this.tgKey == null ? other.tgKey != null : !this.tgKey.equals(other.tgKey)) {
            return false;
        }
        return !(this.viewkey == null ? other.viewkey != null : !this.viewkey.equals(other.viewkey));
    }
}

