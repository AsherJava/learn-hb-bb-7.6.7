/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.bi.dataset.model;

import com.jiuqi.bi.dataset.model.DSModel;
import org.json.JSONObject;

public class DefaultDSModel
extends DSModel {
    private String type;

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    protected void saveExtToJSON(JSONObject json) throws Exception {
    }

    @Override
    protected void loadExtFromJSON(JSONObject json) throws Exception {
    }
}

