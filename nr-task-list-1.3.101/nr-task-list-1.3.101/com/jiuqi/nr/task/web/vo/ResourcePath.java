/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.web.vo;

public class ResourcePath {
    private String key;
    private String title;
    private String icon;
    private String[] path;

    public ResourcePath() {
    }

    public ResourcePath(String key, String title, String icon) {
        this.key = key;
        this.title = title;
        this.icon = icon;
    }

    public ResourcePath(String key, String title, String icon, String ... keys) {
        this.key = key;
        this.title = title;
        this.icon = icon;
        this.path = keys;
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

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String[] getPath() {
        return this.path;
    }

    public void setPath(String[] path) {
        this.path = path;
    }
}

