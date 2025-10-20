/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.bi.oss.storage.disk;

import com.jiuqi.bi.oss.storage.StorageConfig;
import com.jiuqi.bi.oss.storage.StorageConfigParam;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class DiskStorageConfig
extends StorageConfig {
    private String basePath;
    private int partationSize = 10;
    private boolean createIfNotExist = true;
    private boolean encrypt = true;
    private static final String ATTR_BASE_PATH = "dir";
    private static final String ATTR_ENCRYPT = "encrypt";
    private static final String ATTR_PARTITION_SIZE = "partitionSize";

    public DiskStorageConfig() {
    }

    public DiskStorageConfig(String basePath) {
        this.basePath = basePath;
    }

    public String getBasePath() {
        return this.basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public void setPartationSize(int partationSize) {
        this.partationSize = partationSize;
    }

    public int getPartationSize() {
        return this.partationSize;
    }

    public boolean isCreateIfNotExist() {
        return this.createIfNotExist;
    }

    public void setCreateIfNotExist(boolean createIfNotExist) {
        this.createIfNotExist = createIfNotExist;
    }

    public boolean isEncrypt() {
        return this.encrypt;
    }

    public void setEncrypt(boolean encrypt) {
        this.encrypt = encrypt;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.putOpt(ATTR_BASE_PATH, (Object)this.basePath);
        json.putOpt(ATTR_PARTITION_SIZE, (Object)this.partationSize);
        json.putOpt(ATTR_ENCRYPT, (Object)this.encrypt);
        return json;
    }

    @Override
    public void fromJson(JSONObject jsonObject) {
        this.basePath = jsonObject.optString(ATTR_BASE_PATH);
        this.partationSize = jsonObject.optInt(ATTR_PARTITION_SIZE, 10);
        this.encrypt = jsonObject.optBoolean(ATTR_ENCRYPT, true);
    }

    public String toString() {
        return "\u78c1\u76d8\u76ee\u5f55: " + this.basePath;
    }

    @Override
    public List<StorageConfigParam> params() {
        ArrayList<StorageConfigParam> p = new ArrayList<StorageConfigParam>();
        StorageConfigParam storePathConfigItem = new StorageConfigParam(ATTR_BASE_PATH, "\u8def\u5f84", false);
        storePathConfigItem.setDesc("\u6587\u4ef6\u5728\u78c1\u76d8\u4e0a\u7684\u5b58\u50a8\u4f4d\u7f6e");
        p.add(storePathConfigItem);
        StorageConfigParam cp = new StorageConfigParam(ATTR_ENCRYPT, "\u52a0\u5bc6\u6570\u636e", "true", true);
        cp.setWidgetType("checkbox");
        p.add(cp);
        return p;
    }
}

