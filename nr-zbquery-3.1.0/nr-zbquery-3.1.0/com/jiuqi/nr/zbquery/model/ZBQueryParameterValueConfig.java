/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 *  com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.nr.zbquery.model;

import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class ZBQueryParameterValueConfig
extends AbstractParameterValueConfig {
    private List<String> candidateValue = new ArrayList<String>();

    public List<String> getCandidateValue() {
        return this.candidateValue;
    }

    public void setCandidateValue(List<String> candidateValue) {
        this.candidateValue = candidateValue;
    }

    public void toJson(JSONObject json, AbstractParameterDataSourceModel datasource) throws JSONException {
        super.toJson(json, datasource);
        json.put("candidateValue", this.candidateValue);
    }
}

