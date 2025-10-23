/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  org.json.JSONObject
 */
package com.jiuqi.nr.zbquery.dataset;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.zbquery.model.ZBQueryModel;
import com.jiuqi.nr.zbquery.util.SerializeUtils;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class ZBQueryDSDefine {
    private ZBQueryModel zbQueryModel;
    private Map<String, String> fieldAlias = new HashMap<String, String>();
    private Map<String, String> paramAlias = new HashMap<String, String>();
    public static final String TAG_ZBQUERYMODEL = "zbQueryModel";
    public static final String TAG_FIELDALIAS = "fieldAlias";
    public static final String TAG_PARAMALIAS = "paramAlias";

    public ZBQueryModel getZbQueryModel() {
        return this.zbQueryModel;
    }

    public void setZbQueryModel(ZBQueryModel zbQueryModel) {
        this.zbQueryModel = zbQueryModel;
    }

    public Map<String, String> getFieldAlias() {
        return this.fieldAlias;
    }

    public void setFieldAlias(Map<String, String> fieldAlias) {
        this.fieldAlias = fieldAlias;
    }

    public Map<String, String> getParamAlias() {
        return this.paramAlias;
    }

    public void setParamAlias(Map<String, String> paramAlias) {
        this.paramAlias = paramAlias;
    }

    public JSONObject toJson() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JSONObject jsonObject = new JSONObject(mapper.writeValueAsString((Object)this));
        jsonObject.put(TAG_ZBQUERYMODEL, (Object)new JSONObject(new String(SerializeUtils.jsonSerializeToByte(this.zbQueryModel), StandardCharsets.UTF_8)));
        return jsonObject;
    }

    public void fromJson(JSONObject json) throws Exception {
        JSONObject paramAliasJO;
        JSONObject fieldAliasJO;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JSONObject modelJO = json.optJSONObject(TAG_ZBQUERYMODEL);
        if (modelJO != null) {
            this.zbQueryModel = SerializeUtils.jsonDeserialize(modelJO.toString().getBytes(StandardCharsets.UTF_8));
        }
        if ((fieldAliasJO = json.optJSONObject(TAG_FIELDALIAS)) != null) {
            this.fieldAlias = (Map)mapper.readValue(fieldAliasJO.toString(), HashMap.class);
        }
        if ((paramAliasJO = json.optJSONObject(TAG_PARAMALIAS)) != null) {
            this.paramAlias = (Map)mapper.readValue(paramAliasJO.toString(), HashMap.class);
        }
    }
}

