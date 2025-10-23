/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.storage.StorageConfig
 *  com.jiuqi.bi.oss.storage.StorageConfigParam
 *  org.json.JSONObject
 */
package com.jiuqi.nr.file.oss;

import com.jiuqi.bi.oss.storage.StorageConfig;
import com.jiuqi.bi.oss.storage.StorageConfigParam;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class BlobStorageConfig
extends StorageConfig {
    private String fileGroupKey;

    public void fromJson(JSONObject arg0) {
        this.fileGroupKey = arg0.optString("fileGroupKey");
    }

    public List<StorageConfigParam> params() {
        ArrayList<StorageConfigParam> p = new ArrayList<StorageConfigParam>();
        return p;
    }

    public String toString() {
        return "\u62a5\u8868\u5b58\u50a8";
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        return json;
    }
}

