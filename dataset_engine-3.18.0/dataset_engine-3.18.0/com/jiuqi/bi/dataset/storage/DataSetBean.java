/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.storage;

public class DataSetBean {
    private String guid;
    private String id;
    private String title;
    private String type;
    private String folder;
    private String order;

    public String getGuid() {
        return this.guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getType() {
        return this.type;
    }

    public String getFolder() {
        return this.folder;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}

