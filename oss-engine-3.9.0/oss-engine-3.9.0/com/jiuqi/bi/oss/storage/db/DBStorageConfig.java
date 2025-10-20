/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.bi.oss.storage.db;

import com.jiuqi.bi.oss.storage.IConnectionProvider;
import com.jiuqi.bi.oss.storage.StorageConfig;
import com.jiuqi.bi.oss.storage.StorageConfigParam;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class DBStorageConfig
extends StorageConfig {
    private IConnectionProvider connProvider;

    public DBStorageConfig(IConnectionProvider provider) {
        this.connProvider = provider;
    }

    public IConnectionProvider getDataConnProvider() {
        return this.connProvider;
    }

    @Override
    public JSONObject toJson() {
        return new JSONObject();
    }

    @Override
    public void fromJson(JSONObject jsonObject) {
    }

    public String toString() {
        return "\u6570\u636e\u5e93";
    }

    @Override
    public List<StorageConfigParam> params() {
        return new ArrayList<StorageConfigParam>();
    }
}

