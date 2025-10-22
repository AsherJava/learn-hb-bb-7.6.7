/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.message;

import com.jiuqi.nr.attachment.utils.FileStatus;
import java.util.Date;

public class FileInfo {
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
    private String md5;
    private String secretlevel;
    private String category;
    private String filepoolKey;

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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return this.extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public FileStatus getStatus() {
        return this.status;
    }

    public void setStatus(FileStatus status) {
        this.status = status;
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

    public String getLastModifier() {
        return this.lastModifier;
    }

    public void setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier;
    }

    public Date getLastModifyTime() {
        return this.lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getFileGroupKey() {
        return this.fileGroupKey;
    }

    public void setFileGroupKey(String fileGroupKey) {
        this.fileGroupKey = fileGroupKey;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMd5() {
        return this.md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getSecretlevel() {
        return this.secretlevel;
    }

    public void setSecretlevel(String secretlevel) {
        this.secretlevel = secretlevel;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFilepoolKey() {
        return this.filepoolKey;
    }

    public void setFilepoolKey(String filepoolKey) {
        this.filepoolKey = filepoolKey;
    }
}

