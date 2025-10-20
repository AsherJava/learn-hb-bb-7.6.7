/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.bi.oss;

import java.io.Serializable;
import org.json.JSONObject;

public class Bucket
implements Cloneable,
Serializable {
    private String name;
    private String owner;
    private String desc;
    private int expireTimeInMillis = -1;
    private boolean open;
    private String storageType;
    private String storageConfig;
    private String group;
    private boolean manual;
    private boolean linkWhenExist;
    private String status;
    public static final String BUCKET_STATUS_NORMAL = "rw";
    public static final String BUCKET_STATUS_MIGRATEING = "r";
    public static final String BUCKET_STATUS_MIGRATE_READY = "rr";

    public Bucket() {
    }

    public Bucket(String name) {
        this.name = name;
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

    public boolean isOpen() {
        return this.open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setExpireTime(int expireTimeInMillis) {
        this.expireTimeInMillis = expireTimeInMillis;
    }

    public int getExpireTime() {
        return this.expireTimeInMillis;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public String getStorageType() {
        return this.storageType;
    }

    public void setStorageConfig(String storageConfig) {
        this.storageConfig = storageConfig;
    }

    public String getStorageConfig() {
        return this.storageConfig;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroup() {
        return this.group;
    }

    public boolean isManual() {
        return this.manual;
    }

    public void setManual(boolean manual) {
        this.manual = manual;
    }

    public String getStatus() {
        return this.status;
    }

    public Bucket setStatus(String status) {
        this.status = status;
        return this;
    }

    public void setLinkWhenExist(boolean linkWhenExist) {
        this.linkWhenExist = linkWhenExist;
    }

    public boolean isLinkWhenExist() {
        return this.linkWhenExist;
    }

    public Bucket clone() {
        try {
            return (Bucket)super.clone();
        }
        catch (CloneNotSupportedException e) {
            return this;
        }
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.putOpt("name", (Object)this.name);
        json.putOpt("owner", (Object)this.owner);
        json.putOpt("open", (Object)this.open);
        if (this.desc != null) {
            json.putOpt("desc", (Object)this.desc);
        }
        json.putOpt("expireTime", (Object)this.expireTimeInMillis);
        if (this.storageType != null) {
            json.putOpt("storageType", (Object)this.storageType);
        }
        if (this.storageConfig != null) {
            json.putOpt("storageConfig", (Object)this.storageConfig);
        }
        if (this.group != null) {
            json.putOpt("group", (Object)this.group);
        }
        json.putOpt("manual", (Object)this.manual);
        json.putOpt("status", (Object)this.status);
        json.putOpt("link", (Object)this.linkWhenExist);
        return json;
    }
}

