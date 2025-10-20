/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.bi.oss.storage;

import com.jiuqi.bi.oss.storage.StorageConfigParam;
import java.util.List;
import org.json.JSONObject;

public abstract class StorageConfig {
    public abstract List<StorageConfigParam> params();

    public abstract JSONObject toJson();

    public abstract void fromJson(JSONObject var1);
}

