/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.file.impl;

import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileStatus;
import java.util.Date;

public class FileInfoImpl
implements FileInfo {
    private static final long serialVersionUID = -7920071435168928894L;
    private String key;
    private String area;
    private String name;
    private String extension;
    private FileStatus status;
    private long size;
    private String creater;
    private Date createTime;
    private String lastModifier;
    private Date lastModifyTime;
    private int version;
    private String fileGroupKey;
    private String path;
    private String secretlevel;

    @Override
    public String getSecretlevel() {
        return this.secretlevel;
    }

    public void setSecretlevel(String secretlevel) {
        this.secretlevel = secretlevel;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getExtension() {
        return this.extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    @Override
    public FileStatus getStatus() {
        return this.status;
    }

    public void setStatus(FileStatus status) {
        this.status = status;
    }

    @Override
    public long getSize() {
        return this.size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public String getCreater() {
        return this.creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    @Override
    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String getLastModifier() {
        return this.lastModifier;
    }

    public void setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier;
    }

    @Override
    public Date getLastModifyTime() {
        return this.lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    @Override
    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String getArea() {
        return this.area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public String getFileGroupKey() {
        return this.fileGroupKey;
    }

    public void setFileGroupKey(String fileGroupKey) {
        this.fileGroupKey = fileGroupKey;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

