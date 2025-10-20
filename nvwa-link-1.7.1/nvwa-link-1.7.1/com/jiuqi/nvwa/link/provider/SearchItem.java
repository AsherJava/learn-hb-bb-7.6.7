/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.link.provider;

import java.util.List;
import java.util.stream.Collectors;

public class SearchItem {
    private String key;
    private String title;
    private String icons;
    private String extData;
    private List<String> titlePaths;
    private List<String> keyPaths;
    private String titles;

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

    public String getIcons() {
        return this.icons;
    }

    public void setIcons(String icons) {
        this.icons = icons;
    }

    public String getExtData() {
        return this.extData;
    }

    public void setExtData(String extData) {
        this.extData = extData;
    }

    public List<String> getTitlePaths() {
        return this.titlePaths;
    }

    public void setTitlePaths(List<String> titlePaths) {
        this.titlePaths = titlePaths;
    }

    public List<String> getKeyPaths() {
        return this.keyPaths;
    }

    public void setKeyPaths(List<String> keyPaths) {
        this.keyPaths = keyPaths;
    }

    public String getTitles() {
        return this.getTitlePaths().stream().collect(Collectors.joining("/"));
    }
}

