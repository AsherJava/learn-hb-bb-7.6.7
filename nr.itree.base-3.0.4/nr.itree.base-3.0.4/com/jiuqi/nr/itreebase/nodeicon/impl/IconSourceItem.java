/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.itreebase.nodeicon.impl;

import java.util.Objects;

public class IconSourceItem {
    private String key;
    private String icon;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        IconSourceItem iconData = (IconSourceItem)o;
        return Objects.equals(this.key, iconData.key) && Objects.equals(this.icon, iconData.icon);
    }

    public int hashCode() {
        return Objects.hash(this.key, this.icon);
    }
}

