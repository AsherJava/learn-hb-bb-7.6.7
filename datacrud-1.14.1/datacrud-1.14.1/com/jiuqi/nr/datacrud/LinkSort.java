/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacrud;

import com.jiuqi.nr.datacrud.SortMode;
import java.util.Objects;

public class LinkSort {
    private String linkKey;
    private SortMode mode;

    public LinkSort() {
    }

    public LinkSort(String linkKey, SortMode mode) {
        this.linkKey = linkKey;
        this.mode = mode;
    }

    public String getLinkKey() {
        return this.linkKey;
    }

    public void setLinkKey(String linkKey) {
        this.linkKey = linkKey;
    }

    public SortMode getMode() {
        if (this.mode == null) {
            return SortMode.ASC;
        }
        return this.mode;
    }

    public void setMode(SortMode mode) {
        this.mode = mode;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        LinkSort linkSort = (LinkSort)o;
        if (!Objects.equals(this.linkKey, linkSort.linkKey)) {
            return false;
        }
        return this.mode == linkSort.mode;
    }

    public int hashCode() {
        int result = this.linkKey != null ? this.linkKey.hashCode() : 0;
        result = 31 * result + (this.mode != null ? this.mode.hashCode() : 0);
        return result;
    }
}

