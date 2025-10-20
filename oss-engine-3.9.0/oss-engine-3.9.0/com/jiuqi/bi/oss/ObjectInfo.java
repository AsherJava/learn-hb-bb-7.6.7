/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.bi.oss;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class ObjectInfo {
    private String key;
    private String name;
    private String owner;
    private String dir;
    private String extension;
    private String contentType;
    private String md5;
    private long size;
    private String createTime;
    private Map<String, String> extProp = new HashMap<String, String>();

    public ObjectInfo() {
    }

    public ObjectInfo(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getExtension() {
        return this.extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getMd5() {
        return this.md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getDir() {
        return this.dir;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return this.contentType;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Map<String, String> getExtProp() {
        return this.extProp;
    }

    public String convertExtPropToJson() {
        if (this.extProp.isEmpty()) {
            return null;
        }
        JSONObject json = new JSONObject();
        for (Map.Entry<String, String> entry : this.extProp.entrySet()) {
            json.put(entry.getKey(), (Object)entry.getValue());
        }
        return json.toString();
    }

    public String toString() {
        return "ObjectInfo{key='" + this.key + '\'' + ", name='" + this.name + '\'' + ", owner='" + this.owner + '\'' + ", dir='" + this.dir + '\'' + ", contentType='" + this.contentType + '\'' + ", extension='" + this.extension + '\'' + ", md5='" + this.md5 + '\'' + ", size=" + this.size + ", createTime='" + this.createTime + '\'' + ", extProp=" + this.extProp + '}';
    }
}

