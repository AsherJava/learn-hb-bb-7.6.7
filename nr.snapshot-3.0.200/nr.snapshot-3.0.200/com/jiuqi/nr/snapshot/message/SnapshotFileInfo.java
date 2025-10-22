/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.snapshot.message;

import java.util.Date;

public class SnapshotFileInfo {
    private String key;
    private String area;
    private String extension;
    private long size;
    private String creater;
    private Date createTime;
    private String path;

    public SnapshotFileInfo() {
    }

    public SnapshotFileInfo(String key, String area, String extension, long size, String creater, Date createTime) {
        this.key = key;
        this.area = area;
        this.extension = extension;
        this.size = size;
        this.creater = creater;
        this.createTime = createTime;
    }

    public SnapshotFileInfo(String key, String area, String extension, long size, String creater, Date createTime, String path) {
        this.key = key;
        this.area = area;
        this.extension = extension;
        this.size = size;
        this.creater = creater;
        this.createTime = createTime;
        this.path = path;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getArea() {
        return this.area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getExtension() {
        return this.extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getCreater() {
        return this.creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

