/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.DSModel
 *  org.json.JSONObject
 */
package com.jiuqi.nr.zbquery.engine.dataset;

import com.jiuqi.bi.dataset.model.DSModel;
import org.json.JSONObject;

public class QueryDSModel
extends DSModel {
    public static final String TYPE = "com.jiuqi.nr.zbquery";

    public String getType() {
        return TYPE;
    }

    protected void saveExtToJSON(JSONObject json) throws Exception {
    }

    protected void loadExtFromJSON(JSONObject json) throws Exception {
    }
}

