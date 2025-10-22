/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.splittable.web;

import java.io.Serializable;

public class Region
implements Serializable {
    private String regionKey;
    private String title;

    public Region() {
    }

    public Region(String regionKey, String title) {
        this.regionKey = regionKey;
        this.title = title;
    }

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

