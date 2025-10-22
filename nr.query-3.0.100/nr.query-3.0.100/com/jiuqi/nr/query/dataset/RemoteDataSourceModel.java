/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.nr.query.dataset;

import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import org.json.JSONException;
import org.json.JSONObject;

public class RemoteDataSourceModel
extends AbstractParameterDataSourceModel {
    protected void fromExtJson(JSONObject json) throws JSONException {
        super.fromExtJson(json);
    }

    protected void toExtJson(JSONObject json) throws JSONException {
        super.toExtJson(json);
    }

    public String getType() {
        return null;
    }
}

